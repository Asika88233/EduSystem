function openMenu(menuId){
	var a = $("a[menu_id="+menuId+"]");
	var menuName = a.text();
	$("#menu_title").text(menuName);
	var url = "/menu/"+menuId;
	$("#main_content").load(url);
}