<%--
  Created by IntelliJ IDEA.
  User: exbrayat
  Date: 18/09/18
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Hello ${dto.nomMembre}</title>

    <script type="application/javascript">
        function participer(ip) {
            // rustique : on soumet un formulaire...
            var form = document.createElement("form");
            form.setAttribute("method", "POST");
            form.setAttribute("action", "http://localhost:8080/correctiontd1_war/");

            var intp = document.createElement("input");
            intp.setAttribute("type", "hidden");
            intp.setAttribute("name", "intituleP");
            intp.setAttribute("value", ip);
            form.appendChild(intp);

            var todo=document.createElement("input");
            todo.setAttribute("type", "hidden");
            todo.setAttribute("name", "TODO");
            todo.setAttribute("value", "participer");
            form.appendChild(todo);

            //Ajout du formulaire à la page et soumission du formulaire
            document.body.appendChild(form);
            form.submit();
        }
    </script>
</head>
<body>

<p>Salut à toi, O ${dto.nomMembre}.
</p>

Tes compétences sont :
<ul>
    <c:forEach items="${dto.competences}" var="comp">
        <li> ${comp}</li>
    </c:forEach>
</ul>

Les projets :
<ul>
    <li> dont tu es responsable :
        <c:if test="${empty dto.responsable}">
            <p><i>Liste vide</i></p>
        </c:if>
        <c:if test="${not empty dto.responsable}">
        <ul>
            <c:forEach items="${dto.responsable}" var="projet">
                <li> ${projet}</li>
            </c:forEach>
        </ul>
            <p/>

        </c:if>
    </li>
    <li> dont tu es simple participant :
        <c:if test="${empty dto.participe}">
            <p><i>Liste vide</i></p>
        </c:if>
        <c:if test="${not empty dto.participe}">
        <ul>
            <c:forEach items="${dto.participe}" var="projet">
                <li> ${projet}</li>
            </c:forEach>
        </ul>
            <p/>

        </c:if>
    </li>
    <li> pour lesquels tu es compétent :
        <c:if test="${empty dto.competent}">
            <p><i>Tu participes déjà à tous les projets pour lesquels tu es compétent...</i></p>
        </c:if>
        <c:if test="${not empty dto.competent}">
        <ul>
            <c:forEach items="${dto.competent}" var="projet">
                <li> ${projet}</li>
            </c:forEach>
        </ul>
            <p/>
        </c:if>
    </li>

    <li> les autres projets :
        <c:if test="${empty dto.autres}">
            <p><i>Liste vide</i></p>
        </c:if>
        <c:if test="${not empty dto.autres}">
        <ul>
            <c:forEach items="${dto.autres}" var="projet">
                <li> ${projet}</li>
            </c:forEach>
        </ul>
            <p/>

        </c:if>
    </li>
</ul>

</body>
</html>
