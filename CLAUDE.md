# CLAUDE.md

このファイルは、このリポジトリで作業する際にClaude Code (claude.ai/code) に対してガイダンスを提供します。

## 言語設定

すべての応答は日本語で行ってください。

## プロジェクト概要

ImageConversionは、Spring Boot 3.5とJSPで構築された画像処理Webアプリケーションです。リサイズ、トリミング、拡張子変換、OCR（Tesseractを使用した光学文字認識）などの画像操作機能を提供します。

## ビルドと実行コマンド

```bash
# プロジェクトのビルド
./gradlew build

# アプリケーションの実行
./gradlew bootRun

# テストの実行
./gradlew test

# 特定のテストクラスを実行
./gradlew test --tests "com.imageconversion.common.utils.SanitizeTest"

# ビルド成果物のクリーンアップ
./gradlew clean
```

アプリケーションは `http://localhost:8080/ImageConversion/` で起動します

## アーキテクチャ

### 技術スタック
- **フレームワーク**: Spring Boot 3.5.10（組み込みTomcat）
- **ビュー層**: JSP with JSTL
- **画像処理**: Java AWT、javax.imageio、Tesseract（tess4j 5.16.0）
- **テスト**: JUnit 5、Mockito、Spring Boot Test

### パッケージ構造

```
com.imageconversion
├── Application.java              # Spring Bootエントリーポイント
├── ApplicationConfig.java        # アプリケーション全体の設定値
├── config/
│   ├── ServletConfig.java       # Servlet登録Bean
│   └── WebConfig.java           # JSPビューリゾルバー設定
├── conversion/
│   ├── servlet/                 # 画像操作用のHttpServlet実装
│   └── logic/                   # ビジネスロジックのインターフェースと実装
├── common/
│   ├── enums/                   # ImageExtension列挙型
│   ├── exception/               # ビジネス層エラー用のLogicException
│   └── utils/                   # EncodingUtil、FileToPart、Sanitize
└── sample/                      # サンプル/デモ機能
```

### Servletアーキテクチャ

このプロジェクトは、Springビーンとして登録された**従来型のHttpServlet**を使用しています（@Controllerや@RestControllerではありません）。各サーブレットは特定の画像処理機能を担当します：

- `InputImageServlet` - 画像ファイルのアップロードと検証
- `ResizeServlet` - 指定された寸法への画像リサイズ
- `TrimmingServlet` - 画像のトリミング
- `ChangeExtensionServlet` - 画像フォーマットの変換（JPEG、PNGなど）
- `GetExtensionServlet` - 現在の画像拡張子の取得
- `OpticalCharacterRecognitionServlet` - Tesseractを使用した画像からのテキスト抽出
- `SaveImageServlet` - 処理済み画像のディスクへの保存

**重要なパターン**: サーブレットはマルチパートフォームデータを受信し、BufferedImageを使用してメモリ内で画像を処理し、結果をBase64文字列にエンコードして、RequestDispatcher経由でJSPページに転送します。

### ビュー層

JSPファイルは `src/main/webapp/` に配置されています：
- `common/` - 再利用可能なheader、footer、headのインクルード
- `function/` - サーブレット機能に対応する機能別ページ
- `exceptionMessage.jsp` - 集約されたエラー表示ページ

JSPビュー解決は`WebConfig.java`で設定されており、ルートプレフィックス`/`と`.jsp`サフィックスが使用されます。

### ロジック層

ビジネスロジックはインターフェース/実装のペアに分離されています：
- `SaveImage` / `SaveImageImpl` - 画像永続化ロジック
- `OpticalCharacterRecognition` / `OpticalCharacterRecognitionImpl` - OCR処理
- `GetExtension` / `GetExtensionImpl` - ファイル拡張子検出

これらはサーブレットから直接インスタンス化されます（Spring DIは使用しません）。手動による依存性注入パターンに従っています。

### 設定

`application.yml` の設定：
- サーバーポート: 8080
- コンテキストパス: `/ImageConversion`
- 最大ファイルアップロードサイズ: 10MB
- アップロードディレクトリ: `C:/Download/`
- 一時ディレクトリ: システムtemp + `/imageconversion`

## 共通パターン

### 画像処理フロー
1. サーブレットがマルチパートリクエストから`Part`オブジェクトを受信
2. `ImageIO.read()`を使用して`BufferedImage`に読み込み
3. 画像を処理（リサイズ、トリミング、変換など）
4. `ImageIO.write()`で`ByteArrayOutputStream`に書き込み
5. Base64文字列にエンコード
6. リクエスト属性として設定し、JSPに転送
7. JSPが`<img src="data:image/jpeg;base64,...>`で表示

### エラー処理
- サーブレットは入力を検証し、`exception`属性とともに`exceptionMessage.jsp`に転送
- ビジネスロジックは説明的なメッセージとともに`LogicException`をスロー
- `ImageIO.read()`後のnull BufferedImageチェックで画像検証

### ファイル処理
- ファイル名エンコードには`EncodingUtil.convertToUTF8()`を使用
- 拡張子抽出には`Sanitize.getFileExtension()`を使用
- 書き込み前にファイル存在を検証し、`mkdirs()`でディレクトリを作成

## テスト

テストは`src/test/java/`のメインコードと同じパッケージ構造に従います：
- サーブレットテストはMockitoを使用してHttpServletRequest/Responseをモック
- ロジックテストはビジネスロジック検証に焦点を当てる
- JUnit 5には`@ExtendWith(MockitoExtension.class)`を使用

## 最近の移行

コードベースは最近、フラットパッケージ（`common.*`、`conversion.*`、`sample.*`）から適切なドメイン階層（`com.imageconversion.*`）へのパッケージ再構築が行われました。Gitステータスにはこのリファクタリングによる多くのリネームファイルが表示されています。
