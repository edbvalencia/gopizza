# GoPizza – Prueba Técnica Back-End (Java + Spring Boot)

GoPizza es una pizzería en Quito que implementó una máquina automatizada capaz de preparar pizzas a partir de órdenes digitales.  
El objetivo de esta prueba es modelar el flujo entre el área que recibe pedidos y la máquina que los produce, usando Java + Spring Boot y arquitectura basada en eventos internos.

## Descripción del Flujo

El sistema tiene dos módulos ("microservicios"):

### 1. Departamento de Órdenes

- Recibe una orden del cliente.
- Registra el pedido y sus pizzas.
- Envía la orden completa a la máquina de producción.
- Escucha las notificaciones de pizzas terminadas.
- Actualiza el estado de la orden hasta finalizarla.

### 2. Departamento de Producción (Máquina)

- Recibe la orden completa.
- Procesa cada pizza una por una.
- Cada vez que termina una pizza, envía una notificación individual al Departamento de Órdenes.

## Objetivo de la Prueba

Implementar este flujo usando:

- Java 21
- Spring Boot
- Arquitectura basada en eventos internos
- Buenas prácticas (DDD opcional pero recomendado)

## El sistema final debe permitir:

- Crear una orden.
- Enviar la orden al departamento de producción.
- Notificar la finalización de cada pizza.
- Actualizar el estado de la orden conforme avanzan las pizzas.

## Se evaluará:

- Claridad de la arquitectura
- Correcto uso de eventos internos
- Flujo completo funcionando
- Limpieza del código
- Buenas prácticas
