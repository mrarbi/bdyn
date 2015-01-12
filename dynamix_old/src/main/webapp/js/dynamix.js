
function confirmBox(msg, form, action) {
	$.msgBox({
		title : "Confirmation",
		content : msg,
		type : "confirm",
		buttons : [ {
			value : "OK"
		}, {
			value : "Annuler"
		} ],
		success : function(result) {
			if (result == "OK") {
				form.action = action;
				laoderDisplay();
				form.submit();
			}
		}
	});
}

function infoBox(msg) {
	$.msgBox({
		title : "Information",
		content : msg,
		type : "info",
		buttons : [ {
			value : "OK"
		} ],
	});
}

function transformDataTables() {
	$('.dataTable').dataTable({
		// "iDisplayLength": nbr,
		"pagingType" : "full_numbers",
		"scrollY" : 300,
		// "paging": false,
		// "displayStart": 5,
		"language" : {
			// "lengthMenu": "Affichage de _MENU_ par page",
			"lengthMenu" : 'Affichage de <select>' + '<option value="10">10</option>' + '<option value="20">20</option>' + '<option value="50">50</option>'
					+ '<option value="100">100</option>' + '<option value="-1">Tous</option>' + '</select> par page',
			"zeroRecords" : "Rien à afficher",
			"info" : "Page _PAGE_ sur _PAGES_",
			"infoEmpty" : "Pas d'enregistrement",
			"infoFiltered" : "(filtré sur _MAX_ en total)",
			"emptyTable" : "Rien à afficher",
			"infoPostFix" : "",
			"thousands" : ",",
			"loadingRecords" : "Chargement...",
			"processing" : "Chargement...",
			"search" : "Recherche:",
			"paginate" : {
				"first" : "|<",
				"last" : ">|",
				"next" : ">>",
				"previous" : "<<"
			},
			"aria" : {
				"sortAscending" : ": activate to sort column ascending",
				"sortDescending" : ": activate to sort column descending"
			}
		}
	});
}

function checkLigne(id) {
	var checkBoxe = $("#" + id);
	checkBoxe.prop("checked", !checkBoxe.prop("checked"));
}

var overlay = '<div class="overlay"></div>';
var loader = '<div class="loader"></div>';

function laoderDisplay() {
	$('body').append(overlay);
	$('body').append(loader);
}

function laoderRemove() {
	$('body').find('.overlay, .loader').remove();
}

function postForm(form) {
	laoderDisplay();
	form.submit();
}

/*
 * $('.lien_connextion').click(function() { ajaxLaoderDisplay(); return false;
 * }); $('body').not('lien_connextion').click(function() { ajaxLaoderRemove();
 * return false; });
 */