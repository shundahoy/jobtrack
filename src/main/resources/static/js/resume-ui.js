(function () {
  document.querySelectorAll('[data-toggle]').forEach(function (header) {
    header.addEventListener('click', function (e) {
      if (e.target.closest('[data-no-toggle]')) return;
      var targetId = header.getAttribute('data-toggle');
      var body = document.getElementById(targetId);
      var chevron = header.querySelector('.chevron');
      if (!body) return;
      body.classList.toggle('is-hidden');
      if (chevron) chevron.classList.toggle('is-open');
    });
  });

  document.querySelectorAll('[data-char-count]').forEach(function (textarea) {
    var counterId = textarea.getAttribute('data-char-count');
    var counter = document.getElementById(counterId);
    if (!counter) return;
    var max = parseInt(textarea.getAttribute('data-max') || '800', 10);
    var update = function () {
      var len = textarea.value.length;
      counter.textContent = len + ' / ' + max;
      counter.classList.toggle('over-limit', len > max);
    };
    textarea.addEventListener('input', update);
    update();
  });

  document.querySelectorAll('.level-buttons').forEach(function (group) {
    group.querySelectorAll('.level-btn').forEach(function (btn) {
      btn.addEventListener('click', function () {
        group.querySelectorAll('.level-btn').forEach(function (b) { b.classList.remove('active'); });
        btn.classList.add('active');
      });
    });
  });
})();
