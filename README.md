## Introduction

`spring boot demo` is a project for learning and practicing `spring boot`, including other technologies.

## Projects

- [spring-boot-server-sent-events](./spring-boot-server-sent-events) - comunicação em tempo real com Server-Sent Events (SSE).

## Por que usar Server-Sent Events (SSE)?

Este projeto demonstra uma implementação simples, prática e leve de Server-Sent
Events (SSE) utilizando Java, Spring Boot e HTML5 (`EventSource`).

O objetivo é ilustrar como um servidor (**Producer**) pode enviar atualizações
contínuas e em tempo real para o cliente (**Consumer**) via HTTP unidirecional.

Ao contrário de WebSockets, que são bidirecionais e utilizam o protocolo
`ws://`, o SSE funciona sobre o protocolo HTTP padrão em uma conexão persistente
(`text/event-stream`).

- **Servidor (Producer):** mantém a conexão aberta e envia eventos em formato de
  texto conforme eles acontecem.
- **Cliente (Consumer):** o navegador reconecta automaticamente se a conexão
  cair e reage aos eventos recebidos usando a API nativa `EventSource`.

### Demonstração do Server-Sent Events

![Demonstração do projeto Server-Sent Events](./spring-boot-server-sent-events/gif-demo/ServerSentEventsProjectWorks.gif)

## Environment

- **JDK 1.8 +**
- **Maven 3.5 +**
- **IntelliJ IDEA ULTIMATE 2018.2 +**


### License

[MIT](http://opensource.org/licenses/MIT)
