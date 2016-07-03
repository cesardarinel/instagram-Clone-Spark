<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Twitter Client</title>
    <link href='http://fonts.googleapis.com/css?family=Quicksand:300,400' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Lato:400,300' rel='stylesheet' type='text/css'>
    <link href="http://netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/timeline.css">

</head>
<body>

<span class="first"> 

    <div class="avatar" style="float: left">
                <img src="http://www.croop.cl/UI/twitter/images/doug.jpg">
            </div>
    <div>
           <span style="float: left"><a href="usuario/${usuario.getUsername()}">${usuario.getUsername()}</a></span>
           <a class="icon-edit new" href="/crearpost"></a>
     <ahref="/cerrarsesion">Cerrar seccion</a>
    </div>
        </span>
<div style="text-align: center;">
    <ul class="timeline">
    <#list posts as post>
    <li>
        <div class="avatar">
            <img src="http://www.croop.cl/UI/twitter/images/doug.jpg">
        </div>
    <div class="bubble-container">
    <div class="bubble">
    <div class="retweet">
        <span style="float: left">Publicado el: ${post.getFecha()}</span>

        <#if usuario.getUsername()== post.getUsuario().getUsername() >
            <form action="/eliminarpost/${post.getId()}">
                <input style="float: right" type="submit" value="Eliminar">
            </form>
            <form action="/editarpost/${post.getId()}">
                <input style="float: right" type="submit" value="Editar">
            </form>

        </#if>

    </div>
        <img src="images/${post.getImagen()}" width="600" height="600"><br/>
        <h3>${post.getCuerpo()}</h3>
    <#list post.getEtiquetas() as etiqueta>
        <span>#${etiqueta.getEtiqueta()} </span>
    </#list>

        <p style="size: 20sp">Comentarios:</p>
    <#list post.getComentarios() as comentario>
    <div>
       <span><b>${comentario.getUsuario().getUsername()}:</b>${comentario.getComentario()}</span>
        <#if comentario.getUsuario().getUsername()== usuario.getUsername() ||  post.getUsuario().getUsername()== usuario.getUsername() >

       <a style="color: red; float: right" href="/eliminarcomentario/${comentario.getId()}"> <span>eliminar</span></a>
        </#if>
    </div>


    </#list>

        <form method="post" action="/crearcomentario">
            <div class="form-group">
                <input id="comentario" type="text" name="comentario" placeholder="Escribir comentario..."/>
            </div>
            <input type="hidden" name="id_post" value="${post.getId()}"/>
            <button class="btn btn-primary btn-sm">Comentar</button>
        </form>

    </div>
    </div>
    </li>
    </#list>
    </ul>
</div>

</body>
</html>
