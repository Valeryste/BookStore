$(document).ready(function() {
    var csrfToken = $('input[name=_csrf]').val();
    $('.inputQuantity').change(function() {

        var quantity = $(this).val();
        var cart_book_id = $(this).data('book-id');


        $.ajax({
            url: '/cart/update-quantity', // Замените на ваш URL обработчика
            method: 'POST',
            data: {  cart_book_id: cart_book_id, quantity: quantity  },
            dataType: 'json',
            beforeSend: function(xhr) {
                // Включение CSRF-токена в заголовок запроса
                xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
            },
            success: function(response) {
                var totalPrice = response.totalPrice;
                $('#total-price-' + cart_book_id).text(totalPrice);

                var totalPriceCart = response.cart.totalPriceCart
                $('#total-price-cart').text(totalPriceCart);
                console.log(response)
            },
            error: function(xhr, status, error) {
                console.error('Ошибка при выполнении AJAX-запроса:', error);
            }
        });
    });
});