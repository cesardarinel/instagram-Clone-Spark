<#include "*/head/head.ftl">
<body xmlns="http://www.w3.org/1999/html">
<header class="headerTimeline">
    <div class="name fancy-font">
        <a href="usuario/${usuarioSesion.getUsername()}">${usuarioSesion.getUsername()}</a>
    </div>

</header>
<section class="instagram-wrap">
    <div class="container">
        <div class="row">
            <div class="col-xs-12">
                <div class="instagram-content">
                    <div class="row photos-wrap">
                        <!-- Instafeed target div -->
                        <div id="instafeed"></div>
                        <!-- The following HTML will be our template inside instafeed -->
                        <div class="center-block">
                            <h4 style="align-content: center">Usuarios registrados</h4>
                            <#list usuarios as usuario>
                                <div class="header" style="margin: 5%">

                                    <div class="username" style="float: left">
                                        <div class="avatar">
                                            <img src="${usuario.getImagen()!}">
                                        </div>
                                        <div>
                                            <a href="usuario/${usuario.getUsername()}"><h4>${usuario.getUsername()}</h4></a>
                                        </div>

                                    </div>

                                </div>
                            </#list>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

</section>