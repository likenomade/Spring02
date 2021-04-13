/**
 * Ajax jsonView Board Test
 */
// Test 1.
function jsBDetail1(seq) {
	$.ajax({
			type:'Get',
			url:'jbdetail?seq='+seq,
			success:function(resultData) {
			$('#resultArea2').html(resultData.content);
			},
			error: function() {
				alert('~~ 서버 오류 !!! 잠시후 다시 하세요 ~~');
			}
		}); //ajax
} //jsBDetail1

// Test 2.
function jsBDetail2(e,seq,index) {
	console.log('** event Type :' +e.type);
	$.ajax({
			type:'Get',
			url:'jbdetail?seq='+seq,
			success:function(resultData) {
				$('#resultArea2').html('');
				$('.content').html(''); // result span clear
				$('#content'+index).html(resultData.content);
			},
			error: function() {
				alert('~~ 서버 오류 !!! 잠시후 다시 하세요 ~~');
			}
		}); //ajax
} //jsBDetail1

// Test 3.
$(function(){ 
	// Test 3.1) css 활용
	$('.sss').hover(function(e) { // ajax 
	
		// ** 마우스 위치 보관
		// => 매개변수 e 를 이용
		// => e.pageX, e.pageY
		// => e.clientX, e.clientY
		// ** view
		// css => display: none->block
		var mleft = e.pageX;
		var mtop = e.pageY;
		console.log('e.pageX, e.pageY =>' + e.pageX + ' , ' + e.pageY);
		console.log('e.clientX, e.clientY =>' + e.clientX + ' , ' + e.clientY);
		// e.clientX, e.clientY => page scroll 시에 불편
		
		var seq = $(this).attr('id');
		
		$.ajax({
			type:'Get',
			url:'jbdetail?seq='+seq,
			success:function(resultData) {
				$('.content').html(''); // result span clear
				$('#content').html(resultData.content)
					.css({
						display:'Block',
						left:mleft,
						top:mtop						
					}); //css
			},
			error: function() {
				alert('~~ 서버 오류 !!! 잠시후 다시 하세요 ~~');
			}
		}); //ajax
		return false;
	},function() {  // clear
		$('.content').html(''); // result span clear
		$('#content').css({
				display: 'None'
		}); //css
		return false;
	}); //sss_hover 
	
	
	// Test3.2) jQuery 메서드 => show() / hide()
	// => this 와 attr() 값 이용.
	$('.sss2').hover(function(e) {
		var seq = $(this).attr('id');
		var mleft = e.pageX;
		var mtop = e.pageY;
		$.ajax({
			type : 'Get',
			data : {
				seq : seq
			},
			url : 'jbdetail',
			success : function(data) {
				$('.content').html('');
				$('#content').html(data.content).css({
					left : mleft,
					top : mtop
				}).show();
			} // success
		}); // ajax
	}, function() {
		$('.content').html('');
		$('#content').hide();
	});// hover
}); //ready



