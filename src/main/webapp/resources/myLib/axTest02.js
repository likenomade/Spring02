
/*
 * * JQ 로 이벤트 핸들러 작성시 주의 사항 
=> jQuery 로 이벤트를 처리하기 위해 지금처럼 ready 이벤트를 사용하는 경우
   본구문이 포함된 axTest03.js 를 부모창에도 추가하고, 
   결과로 불리워지는 loginForm.jsp 에도 포함 하게되면
   마치 callBack 함수처럼 실행 할 때마다 이중으로 불리워지면서
   2의 자승으로 늘어나게 됨. 
=> 해결방법
  1) ~.js 를 각각 분리한다. 
  -> $('#jlogin').click( .....) 부분 axTest04.js 로 독립 
  2) JS의 함수 방식 으로 이벤트 처리
  
** 이벤트 전달
=> Tag 들이 겹겹이 포함관계에 있을때 이벤트가 전달되어 여러번 발생가능함. 
=> 이것을 이벤트 Propagation 이라함.
=> 해결 : return false
  -> e.preventDefault() + e.stopPropagation()

** ** JS & JQ 에서 익명함수의 매개변수역할 
=> 이벤트 핸들러 의 첫 매개변수 : 이벤트 객체를 전달 
=> ajax 메서드의 success 속성 : 서버의 response 결과 
=> css 속성값 : 0부터 순차적으로 대입됨 (jq03_attr.html 참고)
 */

/*
 ** AjaxTest02
=> axMList , axBList
 */

//*** Ajax MemberList

$(function(){
	$('#amlist').click(function(){
	$.ajax({		
		type:'Get',
		url: 'amlist',
		success:function(resultPage){
			$('#resultArea1').html(resultPage);
	    	} ,
		error:function(){
			alert('서버 오류!! 잠시후 다시하세요');
		 }
	   }); //ajax
	}); //amlist_click
	
//*** 반복문에 이벤트 적용하기 
// test2 2) JQ ,JS ,	
	$('.ccc').click(function(){
		var id = $(this).html();
		console.log('id1 html()=>'+id);
		 id = $(this).text();
		console.log('id2 text()=>'+id);
		 id = $(this).attr('id');
		console.log('id3 attr=>'+id);
		
	$.ajax({		
		type:'Get',
		url: 'idblist',
		data:{
			id:id
		},
		success:function(resultPage){
			$('#resultArea2').html(resultPage);
	    	} ,
		error:function(){
			alert('서버 오류!! 잠시후 다시하세요');
		 }
	   }); //ajax
		return false;
	}); //idblist_click
	
// *** Delete ( JQ, id, class)
   $('.ddd').click(function(){
      var id = $(this).attr('id');  
      $.ajax({
         type:'Get',
         url:'jsdelete',
         data:{
            id:id
         },
         success:function(resultData){
            if (resultData.success=='T') {
               alert("~~ 삭제 성공 ~~");
               // $(this).html('Deleted').removeClass('ddd');
               // ajax 내에서 this 는 이미 변경 되었기 때문에 적용안됨 
               $('#'+id).html('Deleted');
               $('#'+id).off();
               console.log('class test1 => '+ $('#'+id).attr('class'));
            } else {
               alert("~~ 삭제 실패 ~~");
            }
         },
         error:function(){
            alert('서버 오류 발생 !! 잠시후 다시 하세요 ~~');
         }
      }); // ajax
   }) // ddd
	
	
	
});//ready

//*** 반복문에 이벤트 적용하기
//test1 ) JS function 

function aidbList(id) {
   $.ajax({
         type:'Get',
         url:'idblist',
         data: {
            id:id
         },
         success:function(resultPage) {
         $('#resultArea2').html(resultPage);
         },
         error: function() {
            alert('~~ 서버 오류 !!! 잠시후 다시 하세요 ~~');
         }
      }); //ajax
} //aidbList 

