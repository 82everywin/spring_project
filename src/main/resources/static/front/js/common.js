/** 공통 라이브러리 */
const commonLib = {
    /**
    * 요청 헤더 전송용 CSRF 추출
    *
    */
    getCsrfToken() {
        const csrfEl = document.querySelector("meta[name='_csrf']")
        const csrfHeaderEl = document.querySelector("meta[name='_csrf_header']");

        return  { header : csrfHeaderEl.content, token : csrfEl.content };
    },
    /**
    * ajax 요청 편의 메서드
    *
    * @param url - 요청 메서드
    * @method - 요청방식
    * @params - body 요청 데이터
    * @type - 전송 데이터 종류
    */
    ajaxLoad(url, method, params, type) {
        return new Promise((resolve, reject) => {
            method = method || "GET";
            const xhr = new XMLHttpRequest();
            xhr.open(method, url);

            const csrfEl = document.querySelector("meta[name='_csrf']")
            const csrfHeaderEl = document.querySelector("meta[name='_csrf_header']");
            if (csrfEl && csrfHeaderEl) {
                xhr.setRequestHeader(csrfHeaderEl.content, csrfEl.content);
            }

            if (method && ['POST', 'PUT', 'PATCH'].indexOf(method) != -1) {
                type =  type.toUpperCase();
                const contentType = type == 'JSON'? 'application/json':'application/x-www-form-urlencoded';
                xhr.setRequestHeader('content-type', contentType);
                if (type != 'JSON') {
                    params = new URLSearchParams(params).toString();
                }
            } else { // GET, DELETE
                params = null;
            }


           xhr.send(params);

           xhr.onreadystatechange = () => {
                if (xhr.status == 200 && xhr.readyState == XMLHttpRequest.DONE) {
                    resolve(xhr.responseText);
                }
           };

           xhr.onerror = (err) => {
                reject(err);
           };

           xhr.onabort = (err) => {
                reject(err);
           };
        });
    },
    /**
    * 시도 목록
    *
    */
    async getSido() {
        let result = [];
        try {
            const data = await commonLib.ajaxLoad("/ajax/sido");
            if (data) result = JSON.parse(data);
        } catch (err) {
            console.log(err);
        }

        return result;
    },
    /**
    * 시구군 조회
    *
    */
    async getSigugun(sido) {
        let result = [];
        try {
            const data = await commonLib.ajaxLoad("/ajax/sigugun/" + sido);
            if (data) result = JSON.parse(data);
        } catch (err) {
            console.log(err);
        }

        return result;
    }
};

window.addEventListener("DOMContentLoaded", function() {
    /** 시도 변경시 시구군 데이터 조회 및 반영 처리 S */
    const ajaxSidoEls = document.getElementsByClassName("ajax_sido");
    if (ajaxSidoEls.length > 0) {
        for (const el of ajaxSidoEls) {
            el.addEventListener("change", async function() {
                const parentEl = this.parentElement;
                const targetEl = parentEl.querySelector(".ajax_gigugun");
                if (!targetEl) return;
                const sido = this.value.trim();
                targetEl.innerHTML = "<option value=''>- 시구군 선택-</option>";
                if (!sido) return;

                let siguguns = await commonLib.getSigugun(sido);
                if (!siguguns) return;
                targetEl.innerHTML = "";
                for (const sgg of siguguns) {
                    const option = document.createElement("option");
                    option.value = sgg;
                    const optionText = document.createTextNode(sgg);
                    option.appendChild(optionText);

                    targetEl.appendChild(option);
                }

            });
        }
    }

    /** 시도 변경시 시구군 데이터 조회 및 반영 처리 E */

    /** 전체 선택 토글 처리 */
    const chkAlls = document.getElementsByClassName("checkall");
    for (const el of chkAlls) {
        el.addEventListener("click", function() {
            const target = this.dataset.targetName;
            if (!target) return;

            const targets = document.getElementsByName(target);
            for (const ta of targets) {
                ta.checked = this.checked;
            }
        });
    }
    /** 전체 선택 토글 처리 E */

        try {
            CKEDITOR.replace("content", {
                height: 350
            });
        } catch (e) {}

    /** 게시판 카테고리 이동 처리 */
        const bIdEls = document.querySelectorAll("#frmList input[name='bId']");
        for (const el of bIdEls) {
            el.addEventListener("click", function() {
                frmList.submit();
            });
        }
});

/** mypage - qna 접었다 펼치는 js  -> 자꾸 다시 혼자 접혀요.. */   
/*
window.addEventListener("DOMContentLoaded", function() {
    const items = document.querySelectorAll('.question');

  function openCloseAnswer() {
    const answerId = this.id.replace('que', 'ans');

    if(document.getElementById(answerId).style.display === 'block') {
      document.getElementById(answerId).style.display = 'none';
      document.getElementById(this.id + '-toggle').textContent = '+';
    } else {
      document.getElementById(answerId).style.display = 'block';
      document.getElementById(this.id + '-toggle').textContent = '-';
    }
  }

  items.forEach(item => item.addEventListener('click', openCloseAnswer));

 });
*/
