function initialize() {
    var styles = [{
      stylers: [
        { hue: "#00e5ff" },
        { "saturation": -20}
      ]
    },{
      featureType: "road",
      elementType: "geometry.stroke",
      stylers: [
        { "color": "#8FFAFF"},
        { weight: 2.3 },
        { "saturation": -25}
      ]
    },{
      featureType: "water",
      elementType: "geometry",
      stylers: [
        { hue: "#0077ff" },
        { "lightness": -13},
        { "saturation": 80}
      ]
    }];
    var myLatlng = new google.maps.LatLng(59.956141, 30.309229);
    var styledMap = new google.maps.StyledMapType(styles,{name: "Styled Map"});
    var mapOptions = {
        zoom : 17,
        center : myLatlng,
        mapTypeControlOptions: {
            mapTypeIds: [google.maps.MapTypeId.ROADMAP, 'map_style']
        }
    }

    map = new google.maps.Map(document.getElementById('map-canvas'),
            mapOptions);
    map.mapTypes.set('map_style', styledMap);
    map.setMapTypeId('map_style');

    var contentString = '<div class="content">'
            + '<div class="tooltip" id="siteNotice">'
            + '<div id="firstHeading" class="firstHeading"><a>Итмище</a></div>'
            + '<div class="bodyContent">'
            + '<div class="itmo-photo"><img src="img/itmo-photo.jpg" alt="ITMO University" align="right"></div>'
            + '<p><b>Университет ИТМО</b> — крупный государственный вуз Санкт-Петербурга, один из национальных исследовательских университетов России. ИТМО входит в число 15 российских университетов, участников Проекта 5-100 повышения международной конкурентоспособности среди ведущих мировых научно-образовательных центров.</p>'
            + '<p>Научные приоритеты университета — информационные и фотонные технологии.</p>'
            + '<p>Официальный сайт: <a href="http://www.ifmo.ru/">'
            + 'www.ifmo.ru</a> </p>' + '</div>' + '</div>' + '</div>';

    var infowindow = new google.maps.InfoWindow({
        content : contentString
    });
    var marker = new google.maps.Marker({
        position : myLatlng,
        map : map,
        title : 'ITMO',
        icon: 'img/marker.png'
    });
    google.maps.event.addListener(marker, 'click', function() {
        infowindow.open(map, marker);
    });       

    google.maps.event.addListener(infowindow, 'domready', function() {

    // Reference to the DIV that wraps the bottom of infowindow
    var iwOuter = $('.gm-style-iw');
    var iwBackground = iwOuter.prev();

    // Removes background shadow DIV
    iwBackground.children(':nth-child(2)').css({'display' : 'none'});

    // Removes white background DIV
    iwBackground.children(':nth-child(4)').css({'display' : 'none'});

    // Changes the desired tail shadow color.
    iwBackground.children(':nth-child(3)').find('div').children().css({'box-shadow': '0 1px 10px #1946ba', 'z-index' : '1'});

    // Reference to the div that groups the close button elements.
    var iwCloseBtn = iwOuter.next();

    // Apply the desired effect to the close button
    iwCloseBtn.mouseenter(function() {
        $(this).css({'border': '4px solid #3990B9', 'box-shadow': '0 0 15px #113183'});
    }).mouseleave(function() {
        $(this).css({'border': '4px solid #1946ba', 'box-shadow': '0 0 5px #3990B9'});
    });
    iwCloseBtn.css({opacity: '1', right: '43px', top: '5px', border: '4px solid #1946ba', 'border-radius': '13px', 'box-shadow': '0 0 5px #3990B9'});

    // If the content of infowindow not exceed the set maximum height, then the gradient is removed.
    if($('.iw-content').height() < 140){
      $('.iw-bottom-gradient').css({display: 'none'});
    }

    // The API automatically applies 0.7 opacity to the button after the mouseout event. This function reverses this event to the desired value.
    iwCloseBtn.mouseout(function(){
      $(this).css({opacity: '1'});
    });
  });
}

function createMarker(myLatlng, contentString, mytitle) {
    marker = new google.maps.Marker({
        position : myLatlng,
        map : map,
        title : mytitle
    });

    infowindow = new google.maps.InfoWindow({
        content : contentString
    });

    google.maps.event.addListener(marker, 'click', function() {
        infowindow.open(map, marker);
    });
}

// google.maps.event.addDomListener(window, 'load', initialize);