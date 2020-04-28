<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="aplicacion.modelo.pojo.Usuario" %>
<%@page import="aplicacion.modelo.pojo.Producto" %>
<%!Usuario usuario = null; %>
<%!Producto producto = null; %>
<%!String labels = null; %>
<%!String data = null; %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Price Stalker</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.3/dist/Chart.min.js"></script>
</head>
<body>
	<%
		producto = (Producto) request.getAttribute("producto");
		labels = (String) request.getAttribute("labels");
		data = (String) request.getAttribute("data");
		usuario = (Usuario) request.getAttribute("usuario");
	%>
	<ul>
		<li><a href="Principal">Price Stalker</a></li>
		<%
			if(usuario != null){
		%>
		<li><%=usuario.getNombre() %></li>
		<li><a href="AddProducto">Añadir producto a lista</a></li>
		<li><a href="Logout">Cerrar sesión</a></li>
		<li><a href="AdminUser">Administrar usuario</a></li>
		<%} %>
	</ul>
	<h1>Detalles del producto</h1>
	<%
		if(producto != null){
	%>
	<div>
		<div><img src="<%=producto.getImgLink() %>" alt="imágen del producto"></div>
		<div>Nombre: <a href="<%= producto.getLink()%>"><%=producto.getNombre() %></a></div>
		<div>Precio actual: <%=producto.getCoste() %>€</div>
		<div>Precio objetivo: <%=producto.getPrecioObjetivo() %>€</div>
		<div>Fecha de la última revisión: <%=producto.getFecha() %></div>
	</div>
	<%
			if(labels != null & data != null){
	%>
	<canvas id="chart" width="200" height="200"></canvas>
        <script>
            var ctx = document.getElementById('chart').getContext('2d');
            
            var myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: <%=labels%>,
                    datasets: [{
                        label: 'Precios',
                        data: <%=data%>,
                        fill:false,
                        steppedLine:true,
                        backgroundColor: 
                            'rgba(255, 99, 132, 0.2)'
                        ,
                        borderColor: 
                            'rgba(255, 99, 132, 1)'
                        ,
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        yAxes: [{
                            ticks: {
                                // Include a € sign in the ticks
                                callback: function(value, index, values) {
                                    return value+'€';
                                }
                            }
                        }]
                    }
                }
            });
        </script>
        <%
			}
        }
        %>
</body>
</html>