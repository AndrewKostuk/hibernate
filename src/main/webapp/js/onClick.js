function main(id) {
    document.addEventListener('DOMContentLoaded', DOMContentLoaded(id));

    function DOMContentLoaded(id) {
        var buttonNode = document.querySelector('.js-show-form');
        buttonNode.addEventListener('click', showForm(id));
    }

    function showForm(id) {
        var node = document.querySelectorAll('.js-form');
        for (i = 0; i < node.length; i++) {
            var item = node[i];
            if (item.classList.contains(id)) {
                item.classList.remove('hidden');
            }
        }
    }
}


