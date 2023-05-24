const buttons = document.querySelectorAll('.profile_btn_fix');

buttons.forEach(button => {
  button.addEventListener('click', seatFunction, false);
});

function seatFunction() {
  buttons.forEach(btn => btn.classList.remove('active'));
  this.classList.add('active');
}