# spring-boot-server-sent-events

Exemplo de comunicação unidirecional em tempo real usando **Server-Sent Events (SSE)** com Spring Boot.

## Por que usar Server-Sent Events?

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

## Demonstração

![Demonstração do projeto Server-Sent Events](./gif-demo/ServerSentEventsProjectWorks.gif)

## Como executar

```bash
./mvnw spring-boot:run
```

Depois, acesse <http://localhost:8080/> no navegador. A página abre uma conexão com
`GET /api/sse/stream` e exibe as notificações recebidas.

## Configuração

As mensagens são controladas em `src/main/resources/application.properties`:

```properties
sse.event-count=10
sse.event-delay=2s
```

`sse.event-count` define a quantidade de eventos enviados por conexão e
`sse.event-delay` define o intervalo entre eles. O endpoint usa o tipo
`text/event-stream` e nomeia cada evento como `notificacao`.

## Testes

```bash
./mvnw test
```

O teste MVC usa um executor síncrono e valores configurados para validar o fluxo
completo sem aguardar o intervalo real entre as mensagens.
