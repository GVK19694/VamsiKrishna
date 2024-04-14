(function($) {

    var MOBILE_SELECTOR = "mobile.validation",
    foundationReg = $(window).adaptTo("foundation-registry");

    foundationReg.register("foundation.validation.validator", {
        selector: "[data-validation='" + MOBILE_SELECTOR + "']",
        validate: function(el) {
            var mobile_pattern = /^\+?\d{1,3}?\d{10}$/;
            var error_message = "The format must be +(Country Code)Number";
            var result = el.value.match(mobile_pattern);

            if (result === null) {
                return error_message;
            }
        }
    });
    var EMAIL_SELECTOR = "email.validation",
        foundationReg = $(window).adaptTo("foundation-registry");

        foundationReg.register("foundation.validation.validator", {
            selector: "[data-validation='" + EMAIL_SELECTOR + "']",
            validate: function(el) {
                var email_pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                var error_message = "Invalid Email Format";
                var result = el.value.match(email_pattern);

                if (result === null) {
                    return error_message;
                }
            }
        });
}(jQuery));
