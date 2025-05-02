const inputs = document.querySelectorAll(".otp-input");

inputs.forEach((input, index) => {
  input.addEventListener("input", (e) => {
    const value = e.target.value;
    if (/[^0-9]/.test(value)) {
      e.target.value = ""; // clear non-digit input
      return;
    }
    if (value !== "" && index < inputs.length - 1) {
      inputs[index + 1].focus();
    }
  });

  input.addEventListener("keydown", (e) => {
    if (e.key === "Backspace" && input.value === "" && index > 0) {
      inputs[index - 1].focus();
    }
  });

  input.addEventListener("paste", (e) => {
    const paste = (e.clipboardData || window.clipboardData).getData("text").trim();
    if (/^\d{4}$/.test(paste)) {
      paste.split('').forEach((digit, i) => {
        if (inputs[i]) inputs[i].value = digit;
      });
      inputs[3].focus();
    }
    e.preventDefault();
  });
});



//  for timer


  let countdown = 120; // 2 minutes in seconds
  let countdownInterval;

  const updateTimer = () => {
    const minutes = String(Math.floor(countdown / 60)).padStart(2, '0');
    const seconds = String(countdown % 60).padStart(2, '0');
    resendBtn.textContent = `${minutes}:${seconds}`;
  };

  function startTimer() {
    countdownInterval = setInterval(() => {
      countdown--;
      updateTimer();
      
      if (countdown <= 0) {
        clearInterval(countdownInterval);
        resendBtn.disabled = false;
        resendBtn.textContent = "02:00";
      }
    }, 1000);
  }
 

 window.addEventListener("load", function(){
  updateTimer();
  startTimer();
 })

