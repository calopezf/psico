function limitLines(obj, maxPalabras) {
    var valor = obj.value;
    var valorArr = valor.split('\n');
    
    //Se valida las palabras
    for(i=0;i<valorArr.length;i++){
        valorArr[i] = cuentaPalabras(valorArr[i],maxPalabras);
    }
    
    totalRows = valorArr.length;
    
    //Se valida las lineas
    if(valorArr.length >= obj.rows){
        totalRows = obj.rows;
    }
    
    //Arma texto de caja
    var textoConstruido = "";
    for(i=0;i<totalRows;i++){
        textoConstruido = textoConstruido + valorArr[i]+"\n";
    }
    obj.value = textoConstruido.substring(0, textoConstruido.length-1);
}

function cuentaPalabras(txt,maxPalabras) {
    totalWords = txt.split(' ');

    if (totalWords.length > maxPalabras){
        var textoConstruido = "";
        for(i=0;i<maxPalabras;i++){
            textoConstruido = textoConstruido + totalWords[i]+" ";
        }
        
        return textoConstruido.substring(0, textoConstruido.length-1);
    }else{
        return txt;
    }
}

function cuentaPalabrasComponente(obj,maxPalabras) {
    totalWords = obj.value.split(' ');

    if (totalWords.length > maxPalabras){
        var textoConstruido = "";
        for(i=0;i<maxPalabras;i++){
            textoConstruido = textoConstruido + totalWords[i]+" ";
        }
        
        obj.value = textoConstruido.substring(0, textoConstruido.length-1);
    }
}

function acceptNum(e){
    tecla = (document.all) ? e.keyCode : e.which; 
    if (tecla==8 || tecla==9 || tecla==0) return true; 
    patron = /\d/;
    te = String.fromCharCode(tecla); 
    return patron.test(te); 
}

function acceptText(e) { 
    tecla = (document.all) ? e.keyCode : e.which; 
    if (tecla==8 || tecla==9 || tecla==0) return true; 
    patron =/[A-Za-zñÑ\s]/; 
    te = String.fromCharCode(tecla); 
    return patron.test(te); 
} 

function acceptNumText(e) { 
    tecla = (document.all) ? e.keyCode : e.which; 
    if (tecla==8 || tecla==9 || tecla==0) return true; 
    patron = /\w/; // Acepta números y letras
    te = String.fromCharCode(tecla); 
    return patron.test(te); 
}

function cambiaColorFoncdoCeldaHvMouseOver(elem){
    elem.style.backgroundColor = '#ddd';
}

function cambiaColorFoncdoCeldaHvMouseOut(elem){
    elem.style.backgroundColor = '#FFFFFF';
}

   
function tabOnEnter (field, evt) {
    var keyCode = document.layers ? evt.which : document.all ?
    evt.keyCode : evt.keyCode;
    if (keyCode != 13)// && keyCode !=9
        return true;
    else {
        var el=getNextElement(field);
        if (el.type!='hidden')
            el.focus();
        else
            while (el.type=='hidden')
                el=getNextElement(el);
        el.focus();
        return false;
    }
}
function getNextElement (field) {
    var form = field.form;
    for (var e = 0; e < form.elements.length; e++) {
        if (field == form.elements[e])
            break;
    }
    return form.elements[++e % form.elements.length];
}
      
   