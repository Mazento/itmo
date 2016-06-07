$(function() {
    $("#books").on(
     "mouseover", ".book-container img", function() {
        $(this).parent().find(".desc").addClass("opened");
      }
    );
    $("#books").on(
      "mouseout", ".book-container img", function() {
        $(this).parent().find(".desc").removeClass("opened");
      }
    );
});

function getServletResult(url) {
    result = "";
    xhr = new XMLHttpRequest();
    xhr.open("GET", url, false);
    xhr.onload = function (e) {
    if (xhr.readyState === 4)
        if (xhr.status === 200)
            result = xhr.responseText;
    }
    xhr.send(null);
    return result;
}

function loadBook(book) {
    result = getServletResult("/bookshelfservlet?command=loadbook&book=" + book);
    $("#books").append(result);
}

function loadNavigation(page) {
    result = getServletResult("/bookshelfservlet?command=navigation&page=" + page);
    $("#navigation").append(result);
}
