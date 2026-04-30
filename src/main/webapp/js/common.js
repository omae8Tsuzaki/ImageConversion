/**
 * <p>画像の表示</p>
 */

const FILE_SIZE_LIMIT_MB = 10;
const DIMENSION_MAX = 10000;

document.addEventListener("DOMContentLoaded", () => {
	function common() {
		const input = document.querySelector("#file-input");
		if (!input) {
		   console.error("要素 #file-input が見つかりません．");
		   return;
		}
		input.addEventListener(`change`, (e) => {
			const file = e.target.files[0];
			if (!file) {
				console.warn("ファイルが選択されていません．");
				return;
			}

			if (!file.type.startsWith("image/")) {
				showError(input, "画像ファイルを選択してください．");
				input.value = "";
				return;
			}

			if (file.size > FILE_SIZE_LIMIT_MB * 1024 * 1024) {
				showError(input, `ファイルサイズは${FILE_SIZE_LIMIT_MB}MB以下にしてください．`);
				input.value = "";
				return;
			}

			clearError(input);

			const fileReader = new FileReader();
			fileReader.readAsDataURL(file);
			fileReader.addEventListener(`load`, (e) => {
				const imgElm = document.createElement("img");
				imgElm.src = e.target.result;
				const preview = document.querySelector(".preview");
				if (!preview) {
					console.error("要素 .preview が見つかりません．");
				    return;
				}
				preview.innerHTML = "";
				preview.appendChild(imgElm);
			});
			fileReader.addEventListener(`error`, () => {
				showError(input, "ファイルの読み込み中にエラーが発生しました");
			});
		});
	}

	// 数値入力フィールドに上限バリデーションを設定する
	function setupDimensionValidation() {
		const numberInputs = document.querySelectorAll("input[type='number']");
		numberInputs.forEach((input) => {
			input.addEventListener("input", () => {
				const value = parseInt(input.value, 10);
				if (isNaN(value) || value < 1) {
					showError(input, "1以上の整数を入力してください．");
				} else if (value > DIMENSION_MAX) {
					showError(input, `${DIMENSION_MAX.toLocaleString()}以下の値を入力してください．`);
				} else {
					clearError(input);
				}
			});
		});
	}

	// 入力要素の直後にエラーメッセージを表示する
	function showError(inputElm, message) {
		let errorSpan = inputElm.nextElementSibling;
		if (!errorSpan || !errorSpan.classList.contains("input-error")) {
			errorSpan = document.createElement("span");
			errorSpan.classList.add("input-error");
			inputElm.insertAdjacentElement("afterend", errorSpan);
		}
		errorSpan.textContent = message;
	}

	function clearError(inputElm) {
		const errorSpan = inputElm.nextElementSibling;
		if (errorSpan && errorSpan.classList.contains("input-error")) {
			errorSpan.remove();
		}
	}

	common();
	setupDimensionValidation();
});
