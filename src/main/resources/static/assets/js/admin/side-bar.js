// .arrow-ani를 a태그에 추가하면 애니메이션 발동
// 매개변수로는 몇 번째 화살표 메뉴인지를 받는다
function sideAni(idx) {
  subPage(idx);

  $(".arrow-ani").on("click", function (e) {
    e.preventDefault();
    const $img = $(this).find("img.icon__arrow");
    const $subMenuBox = $(this).next(".sub-menu-box__project").eq(0);
    $img.removeClass("transition-remove");
    $subMenuBox.removeClass("transition-remove");
    $img.toggleClass("arrow-rotate");
    $subMenuBox.toggleClass("sub-menu__show");
  });
}

//서브메뉴 페이지는 페이지 이동시 애니매이션이 발생되면 안되고
//애니메이션이 끝난 상태여야 한다.
function subPage(idx) {
  const $img = $(".icon__arrow");
  const $subMenuBox = $(".sub-menu-box__project");

  if (idx !== undefined) {
    $img.eq(idx).addClass("transition-remove arrow-rotate");
    $subMenuBox.eq(idx).addClass("transition-remove sub-menu__show");
  }
}
