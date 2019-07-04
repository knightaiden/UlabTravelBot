$("#replyBtn").click(function(){
    if($('textarea').val()){
        console.log('test submit');
    // $("<p>Hello World!</p>").prependTo("#conversation");
    var megContainer1 = "<div class='row message-body'><div class='col-sm-12 message-main-sender'><div class='sender'><div class='message-text'>";
    var megContainer2 = "</div><span class='message-time pull-right'>";
    var megContainer3 = "</span></div></div></div>"
    var mesg = $("textarea").val();
    var d = new Date($.now());
    var time = d.getDate()+"-"+(d.getMonth() + 1)+"-"+d.getFullYear()+" "+d.getHours()+":"+d.getMinutes();
    console.log(time);
    $('textarea').val('');
    console.log(mesg);
    $("#conversation").append(megContainer1 + mesg + megContainer2 + time + megContainer3);
    $('#conversation').animate({
        scrollTop: $('#conversation').get(0).scrollHeight}, 300); 
    }
    
  });


$("#robotIcon").click(function(){
    console.log('robot click');
    var megContainer1 = "<div class='row message-body'><div class='col-sm-12 message-main-receiver'><div class='receiver'><div class='message-text'>";
    var megContainer2 = "</div><span class='message-time pull-right'>";
    var megContainer3 = "</span></div></div></div>"
    var mesg = $("textarea").val();
    console.log(mesg);
    var d = new Date($.now());
    var time = d.getDate()+"-"+(d.getMonth() + 1)+"-"+d.getFullYear()+" "+d.getHours()+":"+d.getMinutes();
    $('textarea').val('');
    $("#conversation").append(megContainer1 + mesg + megContainer2 + time + megContainer3);
    $('#conversation').animate({
        scrollTop: $('#conversation').get(0).scrollHeight}, 300); 
})