/**
 * Created by hu_minghao on 4/18/17.
 */
function $(obj){
    return document.getElementById(obj);
}
function show(objid){
    $(objid).style.display='inline';
}
function hidden(objid){
    $(objid).style.display='none';
}
function doit(){
    var type_val=$('searchType').value;
    if(type_val==4){
        show('searchByGenre');
        hidden('searchByOther');
    }
    else{
        hidden('searchByGenre');
        show('searchByOther');
    }
}
window.onload=function(){
    var searchType=document.getElementById("searchType");
    searchType.onchange=doit;
    hidden('searchByGenre');
    show('searchByOther');
}

function top100Form() {
    document.getElementById("searchForm").action = "top100";
    document.getElementById("searchForm").submit();
}

function analysisForm() {
    document.getElementById("searchForm").action = "analysis";
    document.getElementById("searchForm").submit();
}