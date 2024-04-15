(function($) {
    var DATE_SELECTOR = "date.validation",
        foundationReg = $(window).adaptTo("foundation-registry");

    foundationReg.register("foundation.validation.validator", {
        selector: "[data-validation='" + DATE_SELECTOR + "']",
        validate: function(el) {
            var date_pattern = /^\d{4}-\d{2}-\d{2}$/;
            var error_message = "The format must be YYYY-MM-DD";
            var result = el.value.match(date_pattern);

            if (result === null) {
                return error_message;
            }
        }
    });
}(jQuery));
