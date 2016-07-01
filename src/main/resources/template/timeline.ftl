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
            ${usuario.getUsername()}
           <a href="/crearpost"><span class="icon-edit new"></span>
               </a>
     <a href="/cerrarsesion">Cerrar seccion
               </a>
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

                    </div>
                    <img src="src\main\resources\img\${post.getImagen()}" width="600" height="600"><br/>
                    <h3>${post.getCuerpo()}</h3>
                    <div class="over-bubble">
                        <div class="icon-star"></div>
                    </div>
                </div>
            </div>
        </li>
        </#list>
    </ul>
</div>

</body>
</html>
