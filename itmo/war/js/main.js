$(function() {
    $("body").on(
     "mouseover", "h3", function() {
        $(this).parent().find(".selectline").addClass("opened");
      }
    );
    $("body").on(
      "mouseout", "h3", function() {
        $(this).parent().find(".selectline").removeClass("opened");
      }
    );
    $(".listHeader").click(function() {
        element = $(this).parent().find(".plate");
        console.log(element.hasClass("hidden"));
        if (element.hasClass("hidden")) {
          element.toggleClass("hidden");
          element.css('position', 'relative');
        }
        else {                
          element.toggleClass("hidden");
          element.delay(400).queue(function(next){$(this).css('position', 'absolute');next();});
        }
    });
});