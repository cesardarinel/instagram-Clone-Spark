<#include "*/head/head.ftl">
<body xmlns="http://www.w3.org/1999/html">
<header class="headerTimeline">
    <div class="name fancy-font">
        <a href="usuario/${usuario.getUsername()}">${usuario.getUsername()}</a>
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

                            <form id="register-form" enctype="multipart/form-data" action="/editarcuenta" method="post" role="form" ">
                                <div class="form-group">
                                    <label for="upfile">Imagen de perfil:</label>
                                        <div class="avatar">
                                            <img src="/${usuario.getImagen()!}">
                                        </div>
                                    <input type="file" name="upfile" tabindex="1"  class="form-control" accept="image/*" value="${upfile!}"><br>

                                </div>
                                <div class="form-group">
                                    <input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email Address" value="${email!}">
                                </div>
                                <div class="form-group">
                                    <input type="text" name="descripcion" id="descripcion" tabindex="1" class="form-control" placeholder="Sobre ti" value="${descripcion!}">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password" value="${password!}">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password2" id="confirm-password" tabindex="2" class="form-control" placeholder="Confirm Password" value="${password2!}">
                                </div>
                              <input type="hidden" value="${usuario.getUsername()}" name="usuario"/>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Update">
                                        </div>
                                    </div>
                                </div>

                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

</section>