document.addEventListener("DOMContentLoaded", function (e) {

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const sessionId = urlParams.get('sessionId');
const username = urlParams.get('username');

let content = document.querySelector('#content');

content.append(`Bem-vindo ${getCookie('login')}, sessão: ${getCookie('sessionId')}`);

});

function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
}