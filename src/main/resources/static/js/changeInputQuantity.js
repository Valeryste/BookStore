$(document).ready(function() {
    var csrfToken = $('input[name=_csrf]').val();
    var previousQuantity = 0;
    $('.inputQuantity').change(function() {

        var quantity = $(this).val();
        var cart_book_id = $(this).data('book-id');


        var isIncreasing = quantity > previousQuantity;

        var isDecreasing = quantity < previousQuantity;

        previousQuantity = quantity;

        var ajaxRequest1 = $.ajax({
            url: '/cart/update-quantity',
            method: 'POST',
            data: { cart_book_id: cart_book_id, quantity: quantity },
            beforeSend: function(xhr) {
                // Включение CSRF-токена в заголовок запроса
                xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
            },
            success: function(response) {

                console.log(response);
            },
        });

        var ajaxRequest2 = $.ajax({
            url: '/cart/updateTotalPriceCart',
            method: 'GET',
            beforeSend: function(xhr) {
                // Включение CSRF-токена в заголовок запроса
                xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
            },
        });

        $.when(ajaxRequest1, ajaxRequest2).done(function(response1, response2) {
            var totalPrice = response1[0].totalPrice;
            $('#total-price-' + cart_book_id).text(totalPrice);

            var totalPriceCart = 0;
            if(isIncreasing) {
                totalPriceCart = response2[0] + response1[0].book.price;
            } else if(isDecreasing) {
                totalPriceCart = response2[0] - response1[0].book.price;
            }
            $('#total-price-cart').text(totalPriceCart);
            console.log($('#total-price-cart'))
            console.log(response2[0]);
        }).fail(function(error1, error2) {
            console.error('Error updating quantity:', error1);
            console.error('Error from another URL:', error2);
        });

    });
});