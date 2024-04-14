(function($) {
    var YEAR_SELECTOR = "year.validation",
        foundationReg = $(window).adaptTo("foundation-registry");

        foundationReg.register("foundation.validation.validator", {
            selector: "[data-validation='" + YEAR_SELECTOR + "']",
            validate: function(el) {
                var date_pattern = /^\d{4}$/;
                var error_message = "The format must be YYYY";
                var result = el.value.match(date_pattern);

                if (result === null) {
                    return error_message;
                }
            }
        });
}(jQuery));
