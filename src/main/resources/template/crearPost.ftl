<#include "*/head/head.ftl">
<script>$(function () {
    var dropZoneId = "drop-zone";
    var buttonId = "clickHere";
    var mouseOverClass = "mouse-over";

    var dropZone = $("#" + dropZoneId);
    var ooleft = dropZone.offset().left;
    var ooright = dropZone.outerWidth() + ooleft;
    var ootop = dropZone.offset().top;
    var oobottom = dropZone.outerHeight() + ootop;
    var inputFile = dropZone.find("input");
    document.getElementById(dropZoneId).addEventListener("dragover", function (e) {
        e.preventDefault();
        e.stopPropagation();
        dropZone.addClass(mouseOverClass);
        var x = e.pageX;
        var y = e.pageY;

        if (!(x < ooleft || x > ooright || y < ootop || y > oobottom)) {
            inputFile.offset({ top: y - 15, left: x - 100 });
        } else {
            inputFile.offset({ top: -400, left: -400 });
        }

    }, true);

    if (buttonId != "") {
        var clickZone = $("#" + buttonId);

        var oleft = clickZone.offset().left;
        var oright = clickZone.outerWidth() + oleft;
        var otop = clickZone.offset().top;
        var obottom = clickZone.outerHeight() + otop;

        $("#" + buttonId).mousemove(function (e) {
            var x = e.pageX;
            var y = e.pageY;
            if (!(x < oleft || x > oright || y < otop || y > obottom)) {
                inputFile.offset({ top: y - 15, left: x - 160 });
            } else {
                inputFile.offset({ top: -400, left: -400 });
            }
        });
    }

    document.getElementById(dropZoneId).addEventListener("drop", function (e) {
        $("#" + dropZoneId).removeClass(mouseOverClass);
    }, true);

})</script>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h3>Crea un nuevo post:</h3>
            <form method="post" action="/crearpost" enctype="multipart/form-data">
                <div class="center-block">
                    <label>Imagen:</label>
                    <div id="drop-zone">
                        Drop files here...

                        <div id="clickHere">
                            or click here..
                            <input type="file" name="upfile"  accept="image/*  onchange="readURL(this)"  />

                        </div>

        </div>
                <div class="form-group">
                    <label>Contenido</label>
                    <textarea type="text" name="contenido" class="form-control">${post.getCuerpo()}</textarea>
                </div>

                <div class="form-group">
                    <label>Etiquetas</label>
                    <input  type="text" name="etiquetas" class="form-control" value="${stringEtiquetas}" />
                </div>

                <!-- <input type="hidden" value="${post.getId()}" name="id" /> -->
                <div class="input-group">
                    <button class="btn btn-success">Procesar</button>
                </div>
            </form>
        </div>
    </div>
</div>
