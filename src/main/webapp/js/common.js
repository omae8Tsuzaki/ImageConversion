/**
 * <p>画像の表示</p>
 */

function common() {
	const input = document.querySelector("#file-input");// idセレクタ
	if (!input) {
	   console.error("要素 #file-input が見つかりません．");
	   return;
	}
	console.log("jsが通ることを確認");
	// 読み込み後の処理
	input.addEventListener(`change`, (e) => {
		const file = e.target.files[0];//最初のファイルを取得
		if (!file) {
			console.warn("ファイルが選択せれていません．");
			return;
		}

		// 画像以外が選択された場合
		if (!file.type.startsWith("image/")) {
			alert("画像ファイルを選択してください．");
			return;
		}

		const fileReader = new FileReader();

		// 画像の読み込み
		fileReader.readAsDataURL(file);

		// 読み込み後の処理
		fileReader.addEventListener(`load`, (e) => {
			const imgElm = document.createElement("img");
			imgElm.src = e.target.result;
			
			// プレビューエリアの取得とクリア
			const preview = document.querySelector(".preview");// クラスセレクタ
			if (!preview) {
				console.error("要素 .preview が見つかりません．");
			    return;
			}
			preview.innerHTML = "";
			preview.appendChild(imgElm);
		});
		// エラーハンドリング
		fileReader.addEventListener(`error`, () => {
			alert("ファイルの読み込み中にエラーが発生しました");
		});
	});
}
common();
