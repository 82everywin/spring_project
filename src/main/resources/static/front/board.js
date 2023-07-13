/** 게시판 작성은 수정 양식 */

window.addEventListener("DOMContentLoaded", function() {
    try {
        CKEDITOR.replace("content", {
            height: 350
        });
    } catch (e) {}
});