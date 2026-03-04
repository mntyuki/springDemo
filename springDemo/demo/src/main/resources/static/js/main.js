/**
 * 書籍追加用カート
 */

const token = document.querySelector("meta[name='_csrf']").content;
const header = document.querySelector("meta[name='_csrf_header']").content;

function addToCart(bookId) {
	//クリックで受け取ったidのurlへpost送信
	fetch('/apicart/add/' + bookId, {
		method: 'POST',
		headers: {
			[header]: token  //AJAXは自動でCSRFトークンが付かないため手動で付与
		}
	})
		.then(response => response.text())
		.then(total => {
			document.getElementById("cartTotal").innerText =
				"合計: " + total + "円";
		});
}

window.onload = function() {
	fetch("/cart/total")
}