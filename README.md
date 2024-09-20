## Spring Boot Demo

Este repositório contém diversos exemplos de projetos Spring Boot.

### 1. [Spring Boot com Actuator](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-actuator)
**O que é:** Actuator é um conjunto de funcionalidades de produção prontas para uso que você pode adicionar à sua aplicação Spring Boot. Ele fornece informações detalhadas sobre o funcionamento da sua aplicação, como métricas, health checks, e muito mais.
**Vantagens:**
* Monitoramento em tempo real da aplicação.
* Detecção e diagnóstico de problemas.
* Exposição de endpoints para configuração e administração.

### 2. [Spring Boot com Apache Kafka](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-apache-kafka-producer-and-consumer)
**O que é:** Apache Kafka é uma plataforma distribuída de streaming de eventos, projetada para publicar, armazenar, processar e consumir fluxos de eventos em tempo real. Kafka é amplamente utilizado para criar pipelines de dados em tempo real e sistemas de streaming.
**Vantagens:**
* Alta taxa de transferência e baixa latência para ingestão de dados.
* Suporte a operações distribuídas e escaláveis.
* Garantia de entrega e durabilidade de mensagens.
* Integração com diversos sistemas de big data e microsserviços.

### 3. [Spring Boot com Elastic Search](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-elasticsearch)
**O que é:** Elastic Search é um mecanismo de busca e análise distribuído que permite armazenar, pesquisar e analisar grandes volumes de dados em tempo real. Ele é amplamente utilizado para pesquisas complexas em dados estruturados e não estruturados.
**Vantagens:**
* Alta performance na pesquisa de grandes volumes de dados.
* Escalabilidade horizontal para lidar com grandes quantidades de dados.
* Suporte para análise em tempo real e agregações complexas.

### 4. [Spring Boot com Flyway](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-flyway)
**O que é:** Flyway é uma ferramenta de migração de banco de dados que permite a versão e o controle de mudanças em seu esquema de banco de dados. Ele facilita a automação de migrações, garantindo que a versão correta do banco esteja sincronizada com a versão da aplicação.
**Vantagens:**
* Controle de versão do esquema do banco de dados.
* Suporte a múltiplos bancos de dados.
* Migrações fáceis de aplicar e reversíveis.
* Integração com pipelines de CI/CD.

### 5. [Spring Boot com Form Login](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-form-login)
**O que é:** Form Login é uma forma de autenticação de usuários utilizando formulários HTML em aplicações Spring Boot. Ele permite que os usuários façam login utilizando credenciais (usuário e senha), oferecendo uma interface amigável e amplamente adotada para autenticação web.
**Vantagens:**
* Personalização completa da página de login.
* Fácil integração com diferentes mecanismos de segurança.
* Suporte para gerenciamento de sessões e logout.
* Integração com frameworks de segurança, como Spring Security.

### 6. [Spring Boot com Google Translate](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-google-translate)
**O que é:** A integração do Spring Boot com o Google Translate permite adicionar funcionalidades de tradução automática em tempo real à sua aplicação. Usando as APIs do Google Cloud, você pode traduzir textos de forma dinâmica entre diversos idiomas diretamente em suas aplicações Spring Boot.
**Vantagens:**
* Tradução automática em tempo real.
* Suporte a vários idiomas.

### 7. [Spring Boot com GRPC](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-grpc)
**O que é:** GRPC é um framework de chamada de procedimento remoto (RPC) de código aberto que pode rodar em qualquer ambiente. Ele facilita a comunicação entre serviços distribuídos, usando o Protobuf para serialização de dados. O Spring Boot com GRPC permite integrar essas capacidades de maneira simples dentro de uma aplicação Spring Boot.
**Vantagens:**
* Comunicação eficiente e de baixa latência entre microserviços.
* Suporte a múltiplas linguagens para serviços e clientes.
* Escalabilidade e performance otimizadas para grandes sistemas distribuídos.

### 8. [Spring Boot com iText para Geração de PDF](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-itext-generate-pdf)
**O que é:** O iText é uma biblioteca popular usada para criar e manipular documentos PDF diretamente em Java. Com o Spring Boot, você pode integrá-lo facilmente para gerar PDFs dinâmicos a partir de templates ou dados da sua aplicação.
**Vantagens:**
* Geração dinâmica de relatórios e documentos em PDF.
* Suporte para diversas funcionalidades avançadas de PDF, como tabelas, imagens e gráficos.
* Integração com Spring Boot para manipulação de dados de entrada e saída de forma eficiente.

### 9. [Spring Boot com JDBC](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-jdbc)
**O que é:** Spring Boot com JDBC é uma integração simples e eficaz entre o Spring Framework e o Java Database Connectivity (JDBC), permitindo que você conecte sua aplicação Spring a um banco de dados relacional e execute consultas SQL de forma prática.
**Vantagens:**
* Facilidade de uso com o `JdbcTemplate` para execução de consultas SQL.
* Integração direta com bancos de dados relacionais sem a necessidade de ORM.
* Maior controle sobre as queries SQL.
* Simplificação de transações e gerenciamento de recursos.

### 10. [Spring Boot com JMH Warmup](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-jmh-warmup)
**O que é:** JMH (Java Microbenchmark Harness) é uma ferramenta de benchmarking desenvolvida para testar o desempenho de pequenos trechos de código. No contexto do Spring Boot, o JMH é usado para realizar medições precisas de performance, permitindo avaliar o impacto de otimizações. O warmup é uma fase inicial de aquecimento que garante que o JIT (Just-In-Time) compiler e outras otimizações da JVM sejam aplicadas antes de iniciar a medição real.
**Vantagens:**
* Permite benchmarks precisos e confiáveis.
* Aquecimento para minimizar distorções causadas pelo JIT.
* Identificação de gargalos de desempenho no código.

### 11. [Spring Boot com JPA](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-jpa)
**O que é:** Spring Data JPA é uma parte do Spring Data que facilita o acesso a dados em aplicações Java. Ele simplifica a implementação de repositórios de dados, permitindo que você interaja com bancos de dados usando entidades Java e operações CRUD.

**Vantagens:**
* Redução significativa de código boilerplate para acesso a dados.
* Suporte a consultas personalizadas usando métodos de nomeação.
* Integração fácil com diferentes bancos de dados.
* Gerenciamento automático de transações.
* Suporte a paginação e ordenação de resultados.

### 12. [Spring Boot com MongoDB](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-mongodb)
**O que é:** O Spring Boot com MongoDB é uma integração que permite o desenvolvimento de aplicações Java que utilizam o MongoDB como banco de dados. Ele simplifica a configuração e o uso do MongoDB, permitindo que você se concentre na lógica de negócios da sua aplicação.
**Vantagens:**
* Configuração automática e simplificada do MongoDB.
* Suporte a operações de CRUD de forma intuitiva.
* Integração com o Spring Data, facilitando o acesso e a manipulação de dados.
* Suporte a consultas complexas através de Aggregations.

### 13. [Spring Boot com MyBatis](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-mybatis)
**O que é:** MyBatis é um framework de persistência que facilita o mapeamento de objetos para registros em banco de dados, permitindo o uso de SQL puro. Ele se integra facilmente ao Spring Boot, oferecendo uma abordagem flexível para acesso a dados.
**Vantagens:**
* Suporte a SQL dinâmico e complexidade na consulta.
* Mapeamento simples entre objetos Java e tabelas de banco de dados.
* Redução do código boilerplate em comparação com JDBC.

### 14. [Spring Boot com OpenFeign](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-openfeign)
**O que é:** OpenFeign é uma biblioteca que simplifica a criação de clientes HTTP em aplicações Spring Boot. Com ele, você pode declarar interfaces que representam serviços externos, e o Feign se encarrega de implementar as chamadas HTTP para você.
**Vantagens:**
* Redução da boilerplate code ao criar clientes HTTP.
* Integração fácil com Spring Cloud.
* Suporte a anotação, permitindo configuração intuitiva.
* Suporte a fallback para resiliência em chamadas de serviço.


### 15. [Spring Boot com R2DBC](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-r2dbc)
**O que é:** R2DBC (Reactive Relational Database Connectivity) é uma API para acessar bancos de dados relacionais de forma reativa em aplicações Spring Boot. Ela permite trabalhar com bancos de dados de forma não bloqueante, integrando-se com o modelo de programação reativa do Spring WebFlux.

**Vantagens:**
* Acesso não bloqueante a bancos de dados relacionais.
* Integração com a programação reativa do Spring, facilitando a construção de aplicações escaláveis.
* Suporte a consultas reativas, melhorando a performance em cenários de alta concorrência.

### 16. [Spring Boot com RabbitMQ](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-rabbitmq)
**O que é:** RabbitMQ é um sistema de mensageria que permite a comunicação entre diferentes partes da aplicação através do envio e recebimento de mensagens. O Spring Boot facilita a integração com RabbitMQ, permitindo a construção de sistemas assíncronos e desacoplados.

**Vantagens:**
* Facilita a comunicação entre microserviços.
* Suporte a filas de mensagens para processamento assíncrono.
* Alta disponibilidade e escalabilidade através de clusters.
* Configuração simplificada com anotações do Spring.

### 17. [Spring Boot com WebFlux Reactive](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-reactive)
**O que é:** WebFlux é uma alternativa ao Spring MVC que oferece um modelo reativo para construir aplicações assíncronas e não bloqueantes. Ele é ideal para aplicações que necessitam de alta escalabilidade e desempenho.

**Vantagens:**
* Suporte a programação reativa e não bloqueante.
* Melhor desempenho em cenários de I/O intensivo.
* Integração com projetos reativos como Project Reactor e RxJava.

### 18. [Spring Boot com Redis](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-redis)
**O que é:** O Spring Boot com Redis permite que você integre o Redis, um armazenamento de estrutura de dados em memória, em sua aplicação. Ele facilita o uso de Redis como um cache, banco de dados ou broker de mensagens.
**Vantagens:**
* Aumento significativo na performance da aplicação através de caching.
* Suporte para operações assíncronas e pub/sub.
* Simplicidade na configuração e integração com outras partes do Spring.

### 19. [Spring Boot com Retry](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-retry)
**O que é:** Spring Boot Retry é um módulo que permite que você reexecute operações que falharam devido a exceções temporárias, como problemas de rede ou falhas de serviço. Ele simplifica o tratamento de erros, aplicando uma lógica de repetição configurável.

**Vantagens:**
* Melhora a resiliência da aplicação ao lidar com falhas temporárias.
* Permite configuração flexível de políticas de repetição (número de tentativas, intervalos, etc.).
* Integração fácil com outras funcionalidades do Spring, como serviços e chamadas assíncronas.

### 20. [Spring Boot com Schedule](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-schedule)
**O que é:** O Spring Boot Schedule permite agendar tarefas de forma simples e eficiente, utilizando a anotação `@Scheduled`. Isso é útil para executar tarefas recorrentes em intervalos definidos, como limpeza de dados, envio de e-mails ou atualizações de cache.
**Vantagens:**
* Facilidade de agendamento de tarefas com anotações simples.
* Flexibilidade para definir intervalos de execução (fixo, cron, etc.).
* Suporte a operações assíncronas, melhorando a performance da aplicação.

### 21. [Spring Boot com Twilio SMS](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-sms)
**O que é:** Twilio é uma plataforma de comunicação que permite enviar e receber SMS, fazer chamadas e mais. Integrando Twilio com Spring Boot, você pode facilmente adicionar funcionalidades de envio de SMS à sua aplicação.

**Vantagens:**
* Envio de SMS de forma simples e rápida.
* Integração com outros serviços da Twilio, como chamadas e mensagens de voz.
* Escalabilidade para enviar grandes volumes de mensagens.
* Relatórios e análise de desempenho das mensagens enviadas.

### 22. [Spring Boot com Swagger Documentation](https://github.com/sartorileonardo/spring-boot-demo/tree/main/spring-boot-swagger-documentation)
**O que é:** Swagger é uma ferramenta que permite a documentação automática e a visualização de APIs RESTful. Integrando o Swagger ao Spring Boot, você pode gerar uma interface interativa para testar seus endpoints de maneira simples e eficaz.
**Vantagens:**
* Geração automática de documentação a partir das anotações da sua API.
* Interface interativa para testar endpoints diretamente do navegador.
* Facilita a comunicação entre desenvolvedores e equipes de frontend, oferecendo uma visão clara da API.


## Ambiente

- **JDK 1.8 +**
- **Maven 3.5 +**


### License

[MIT](http://opensource.org/licenses/MIT)

