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
