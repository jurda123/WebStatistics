function submit(){
var letterOccurences =[];
var wordOccurences =[];
var url = $("#url").val();
$("#targeturl").html(url);
$.ajax(
		{
			type:'GET',
			contentType: "application/json; charset=utf-8",
			url: "GetWebData" + "?url=" + encodeURI(url), 
			success: function(result){
    let json = JSON.parse(result);
   
    var words = json.wordOccurences[0];
    var letters = json.letteroccurences[0];
    
    var i =0
    for(i =0;i<words.length;i++){
    	let arr = words[i].split('key:');
    	let arr2 = arr[1].split(',value:');
    	var key = arr2[0]
    	var value = arr2[1];
    	wordOccurences.push({"key":key, "value":parseInt(value)});
    }
    
    
    var j =0
    for(j =0;j<letters.length;j++){
    	let arr = letters[j].split('key:');
    	let arr2 = arr[1].split(',value:');
    	var key = arr2[0]
    	var value = arr2[1];
    	letterOccurences.push({"key":key, "value":parseInt(value)});
    }
   
    
    letterOccurences = letterOccurences.sort((a,b)=>a.value<b.value);
    wordOccurences = wordOccurences.sort((a,b)=>a.key.length<b.key.length);
   
    $('#mostcommonletter').html(letterOccurences[0].key);
    $('#longestword').html(wordOccurences[0].key);
    $('#words').html("<tr><th> word <th>frequency</tr>");
    let index = 0;
    var tableHtml ="";
    wordOccurences = wordOccurences.sort((a,b)=>a.value<b.value);
    for(index; index<wordOccurences.length;index++){
    	let line ="<tr><td>" + wordOccurences[index].key + "</td><td>" + wordOccurences[index].value + "</td></tr>"
    	tableHtml= tableHtml + line;
    	 
    }
    $('#words').html($('#words').html() + tableHtml);
  }, error: function(jqXHR, exception){
	  console.log(jqXHR);
	  $('#error').html = exception;
	  
	  }
  });

}