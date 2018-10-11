$(document).ready(function () {
  $('.events').slick({
    dots: true,
    slidesToShow: 3,
    slidesToScroll: 3,
    accessibility: false,
    responsive: [
      {
        breakpoint: 992,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 2
        }
      },
      {
        breakpoint: 500,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1
        }
      }
    ]
  });

  $('.promotions').slick({
    dots: true,
    slidesToShow: 3,
    slidesToScroll: 3,
    accessibility: false,
    responsive: [
      {
        breakpoint: 992,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 2
        }
      },
      {
        breakpoint: 500,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1
        }
      }
    ]
  });
});
