$("#replyBtn").click(function(){
    handleSubmit();
  });


$("#comment").keypress(function (e) {
if(e.which == 13) {
       handleSubmit();
       event.preventDefault();
   }
});


handleResponse = (response) => {
    console.log('robot response');
    var megContainer1 = "<div class='row message-body'><div class='col-sm-12 message-main-receiver'><div class='receiver'><div class='message-text'>";
    var megContainer2 = "</div><span class='message-time pull-right'>";
    var megContainer3 = "</span></div></div></div>"
    var mesg = response.content;
    console.log(mesg);
    var d = new Date($.now());
    var time = d.getDate()+"-"+(d.getMonth() + 1)+"-"+d.getFullYear()+" "+d.getHours()+":"+d.getMinutes();
    $('textarea').val('');
    $("#conversation").append(megContainer1 + mesg + megContainer2 + time + megContainer3);
    $('#conversation').animate({
        scrollTop: $('#conversation').get(0).scrollHeight}, 300);
}

handleSubmit = () => {
    if($('textarea').val()){
    var megContainer1 = "<div class='row message-body'><div class='col-sm-12 message-main-sender'><div class='sender'><div class='message-text'>";
    var megContainer2 = "</div><span class='message-time pull-right'>";
    var megContainer3 = "</span></div></div></div>"
    var mesg = $("textarea").val();
    var d = new Date($.now());
    var time = d.getDate()+"-"+(d.getMonth() + 1)+"-"+d.getFullYear()+" "+d.getHours()+":"+d.getMinutes();
    console.log(time);
    $('textarea').val('');
//    console.log(mesg);
//    make object
    var sendMsg = new Object();
    sendMsg.topicId = 1;
    sendMsg.speakerId = "";
    sendMsg.type = 1;
    sendMsg.content = mesg;
//    console.log(sendMsg);

//    post json
    $.ajax({
        type: "POST",
    	contentType: "application/json",
    	url: "/sendMsg",
    	data: JSON.stringify(sendMsg),
    	dataType: 'json',
    	timeout: 6000,
        success: function (data, status) {
        console.log(data);
        console.log(status);
        handleResponse(data);
    	},
    	error: function (e) {
    	    alert('Error, try again')
            }
    	});

    $("#conversation").append(megContainer1 + mesg + megContainer2 + time + megContainer3);
    $('#conversation').animate({
        scrollTop: $('#conversation').get(0).scrollHeight}, 300); 
    }
}


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
