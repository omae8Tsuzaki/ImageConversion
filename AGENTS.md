# AGENTS.md

このファイルは、このリポジトリで作業する際にCodex (Codex.ai/code) に対してガイダンスを提供します。

## 言語設定

すべての応答は日本語で行ってください。

## プロジェクト概要

ImageConversionは、Spring Boot 3.5とJSPで構築された画像処理Webアプリケーションです。リサイズ、トリミング、拡張子変換、OCR（Tesseractを使用した光学文字認識）などの画像操作機能を提供します。

## ビルドと実行コマンド

このリポジトリにはGradle Wrapper（`gradlew`）が含まれていないため、ローカルにインストール済みの`gradle`コマンドを使用します。

```bash
# プロジェクトのビルド
gradle build

# アプリケーションの実行
gradle bootRun

# テストの実行
gradle test

# 特定のテストクラスを実行
gradle test --tests "com.imageconversion.common.utils.SanitizeTest"

# ビルド成果物のクリーンアップ
gradle clean
```

アプリケーションは `http://localhost:8080/ImageConversion/` で起動します（`/home/Menu.html` へ自動リダイレクト）

## アーキテクチャ

### 技術スタック
- **フレームワーク**: Spring Boot 3.5.10（組み込みTomcat）
- **ビュー層**: JSP with JSTL
- **画像処理**: Java AWT、javax.imageio、Tesseract（tess4j 5.16.0）
- **テスト**: JUnit 5、Mockito、Spring Boot Test

### パッケージ構造

```
com.imageconversion.common
├── Application.java              # Spring Bootエントリーポイント
├── ApplicationConfig.java        # アプリケーション全体の設定値
├── HomeController.java           # ルートURL（/）をMenu.htmlへリダイレクト
├── WebConfig.java                # JSPビューリゾルバー設定
├── conversion/
│   ├── controller/              # 画像操作用の@Controller実装
│   └── logic/                    # ビジネスロジックのインターフェースと実装
├── enums/                        # ImageExtension列挙型
├── exception/                    # ビジネス層エラー用のLogicException
├── utils/                        # EncodingUtil、FileToPart、FileValidator、Sanitize
└── sample/                       # サンプル/デモ機能
```

**重要**: `Application.java` が `com.imageconversion.common` に配置されているため、Spring Bootのコンポーネントスキャン対象はこのパッケージ配下です。新しいアプリケーションクラス、設定クラス、コントローラー、ロジック、ユーティリティは原則として `com.imageconversion.common` 配下に配置してください。

### コントローラーアーキテクチャ

このプロジェクトは、各画像処理機能を**Spring MVCの`@Controller`**として実装しています。各コントローラーは特定の画像処理機能を担当します：

- `InputImageController` - 画像ファイルのアップロードと検証
- `ResizeController` - 指定された寸法への画像リサイズ
- `TrimmingController` - 画像のトリミング
- `ChangeExtensionController` - 画像フォーマットの変換（JPEG、PNGなど）
- `GetExtensionController` - 拡張子一覧の取得
- `OpticalCharacterRecognitionController` - Tesseractを使用した画像からのテキスト抽出
- `SaveImageController` - 処理済み画像のディスクへの保存

ルートリダイレクト用に`HomeController`も存在します。

**重要なパターン**: コントローラーは`@PostMapping`（一部`@GetMapping`）で`@RequestParam`を介してマルチパートの`Part`やフォーム値を受信し、BufferedImageを使用してメモリ内で画像を処理し、結果をBase64文字列にエンコードして、`Model`に属性を設定してビュー名（例: `"function/resize"`）を返却します。ファイル未選択時は`"redirect:/function/xxx.jsp"`を返します。マルチパート処理はSpring Bootのオートコンフィグ（`application.yml`の`spring.servlet.multipart`）で有効化されます。

> **補足**: かつては各機能を`ServletRegistrationBean`で登録した従来型`HttpServlet`（`conversion/servlet/`配下、`ServletConfig`で登録）として実装していましたが、すべてSpring MVCコントローラーへ移行済みです。新規機能は`@Controller`で実装してください。`HttpServlet`の追加や`ServletConfig`の再導入は行わないでください。

### ビュー層

JSPファイルは `src/main/webapp/` に配置されています：
- `common/` - 再利用可能なheader、footer、headのインクルード
- `function/` - コントローラー機能に対応する機能別ページ
- `function/exceptionMessage.jsp` - 集約されたエラー表示ページ

JSPビュー解決は`WebConfig.java`で設定されており、ルートプレフィックス`/`と`.jsp`サフィックスが使用されます。

### ロジック層

ビジネスロジックはインターフェース/実装のペアに分離されています：
- `SaveImage` / `SaveImageImpl` - 画像永続化ロジック
- `OpticalCharacterRecognition` / `OpticalCharacterRecognitionImpl` - OCR処理
- `GetExtension` / `GetExtensionImpl` - ファイル拡張子検出

実装クラスは`@Service`として登録され、各コントローラーはコンストラクタインジェクション（Spring DI）でインターフェースを受け取ります。

加えて、サンプル機能向けに`Calc`系（`sample/logic`）のロジックも存在します。

### 設定

`application.yml` の設定：
- サーバーポート: 8080
- コンテキストパス: `/ImageConversion`
- 最大ファイルアップロードサイズ: 10MB
- アップロードディレクトリ: `C:/Download/`
- 一時ディレクトリ: システムtemp + `/imageconversion`

## 共通パターン

### 画像処理フロー
1. コントローラーが`@RequestParam`でマルチパートの`Part`オブジェクトを受信
2. `FileValidator.readImage()`で`BufferedImage`に読み込み（無効な場合はIOException）
3. 画像を処理（リサイズ、トリミング、変換など）
4. `ImageIO.write()`で`ByteArrayOutputStream`に書き込み
5. Base64文字列にエンコード
6. `Model`に属性として設定し、ビュー名を返却
7. JSPが`<img src="data:image/jpeg;base64,...>`で表示

### エラー処理
- コントローラーは入力を検証し、`exception`属性を`Model`に設定して`"function/exceptionMessage"`を返却
- ビジネスロジックは説明的なメッセージとともに`LogicException`をスロー
- `FileValidator.readImage()`で画像バリデーション（nullの場合はIOExceptionをスロー）
- `FileValidator.isEmptyFilePart()`でファイル未選択チェック

### ファイル処理
- ファイル名エンコードには`EncodingUtil.convertToUTF8()`を使用
- 拡張子抽出には`Sanitize.getFileExtension()`を使用
- 書き込み前にファイル存在を検証し、`mkdirs()`でディレクトリを作成

## テスト

テストは`src/test/java/`のメインコードと同じパッケージ構造に従います：
- コントローラーテストはロジックをモック化（またはインターフェース実装を直接利用）し、`Model`に`org.springframework.ui.ExtendedModelMap`を渡して、戻り値のビュー名とモデル属性を検証する
- ロジックテストはビジネスロジック検証に焦点を当てる
- Mockitoのモックは`@BeforeEach`内で`mock()`を直接呼び出して作成

## 最近の移行

- 各画像処理機能を従来型`HttpServlet`（`conversion/servlet/`、`ServletConfig`で登録）からSpring MVCの`@Controller`（`conversion/controller/`）へ移行しました。これに伴い`conversion/servlet/`パッケージと`ServletConfig`は削除済みです。新規機能は`@Controller`＋コンストラクタDIで実装してください。
- アプリケーション本体を`com.imageconversion.common.*`配下へ集約するパッケージ再構築が行われています。旧パッケージ参照（例: `com.imageconversion.conversion.*`、`com.imageconversion.sample.*`、`com.imageconversion.config.*`）を追加しないよう注意してください。Gitステータスにはこのリファクタリングによる多くのリネームファイルが表示される場合があります。
