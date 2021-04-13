/**
 *  member input Data 무결성 확인 function
 */
// ** member 오류 확인사항
// ID : 길이(4이상), 영문자,숫자 로만 구성
// password : 길이(4이상), 숫자와 특수문자로 구성, 특수문자는 반드시 1개 이상 포함할것
// Name : 길이(2이상), 영문 또는 한글로 만 입력
// Level : select 를 이용 (X)
// BirthDay : 입력 여부 확인  ( length == 10 )
// Point : 정수의 범위  ( 숫자이면서, '.'이 없어야함 )
// Weight: 구간 (20 ~ 200)
// 추천인 : 필수 사항이 아님 (X)

// ** 작성 규칙
// => JavaScript function 으로 정의 하고 
//    결과를 true or false 로 return

function idCheck() {
   // $(this).val() : this 적용 안됨
   var id=$("#id").val();
   if (id.length < 4) {
      $('#iMessage').html(' ID는 4글자 이상 입력하세요 ~~');
      return false;
   }else if (id.replace(/[a-z.0-9]/gi,'').length > 0 ) {
      $('#iMessage').html(' ID는 영문과 숫자로만 입력하세요 ~~ ');
      return false;
   }else {
      $('#iMessage').html('');
      return true;
   }
} //idCheck

function pwCheck(){
   var password=$("#password").val();
   var pLength = password.length;
   if(pLength <4){
      $('#pMessage').html(' password는 4글자 이상 입력하세요 ~~');
       //$('#password').focus();
       return false;
   }else if(password.replace(/[!-*]/gi,'').length>=pLength){
      $('#pMessage').html(' Password는 특수문자를 반드시 1개 이상 포함해야 됩니다 ~~');
       //$('#password').focus();
       return false;
   }else if (password.replace(/[0-9.!-*]/gi,'').length>0){
      $('#pMessage').html(' Password는 숫자와 특수문자로만 입력하세요 ~~');
       //$('#password').focus();
       return false;
   }else {
      $('#pMessage').html('');
      return true;
   }
} //pwCheck

function nmCheck() {
   var name=$("#name").val();
   if(name.length < 2){
      $('#nMessage').html(' Name 2글자 이상 입력하세요 ~~');
       return false;
   }else if(name.replace(/[a-z.가-힇]/gi,'').length > 0 ){ //다음 확인
         $('#nMessage').html(' Name은 한글과 영문으로만 입력하세요 ~~');   
       return false;
   }else {
       $('#nMessage').html('');
         return true;
   }
} //nmCheck

function bdCheck() {
   var birthd=$("#birthd").val();
   if(birthd.length != 10){
      $('#bMessage').html(' 생일은 YYYY-MM-DD 정확하게 입력하세요 ~~');
       return false;
   }else {
       $('#bMessage').html('');
         return true;
   }
} //bdCheck 

function potCheck() {
   var point=$("#point").val();
   var poLength= point.length;
   if($.isNumeric(point)==false || point.replace(/[.]/g,'').length<poLength ){
         $('#poMessage').html(' Point는 정수로만 입력하세요 ~~');
         return false;
   }else {
        $('#poMessage').html('');
        return true;
   }
} //poCheck 

function weCheck() {
   var weight=$("#weight").val();
    if($.isNumeric(weight)==false){
       $('#wMessage').html(' Weight는 숫자로만 입력하세요 ~~');
        return false;
    }else if ( parseFloat(weight)<20 || parseFloat(weight)>200){
       $('#wMessage').html(' Weight가 범위를 초과합니다 ~~');
       $('#weight').focus();
       return false;
    }else {
       $('#wMessage').html('');
       return true;
   }
} //weCheck 