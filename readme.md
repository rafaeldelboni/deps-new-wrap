# deps-new-wrap
Wrapper over deps-new to give a more user friendly CLI

## Usage
```bash
# For a template that is created with the github username/name as namespace
clj -X:gh :template rafaeldelboni/helix-scratch :name myusername/mynewapp

# If the namespace is different from the repository owner
clj -X:gh :repo rafaeldelboni/helix-scratch-clojars :template cc.delboni/helix-scratch :name myusername/mynewapp
```

### Github
```bash
clj -X:gh :template rafaeldelboni/helix-scratch :name myusername/mynewapp
```
### Gitlab
```bash
clj -X:gl :template rafaeldelboni/helix-scratch :name myusername/mynewapp
```
### Bitbucket
```bash
clj -X:bb :template rafaeldelboni/helix-scratch :name myusername/mynewapp
```
### Sourcehut
```bash
clj -X:sh :template rafaeldelboni/helix-scratch :name myusername/mynewapp
```
### Codeberg
```bash
clj -X:cb :template rafaeldelboni/helix-scratch :name myusername/mynewapp
```
### Git Url
```bash
clj -X:git :url https://github.com/rafaeldelboni/helix-scratch.git :template :cc.delboni/helix-scratch :name myusername/mynewapp
```

## Developing

### Repl
To open a nrepl
```bash
clj -M:dev:nrepl
```

### Run Tests
To run tests inside `./test/*`
```bash
clj -M:dev:test
```

### Uberjar
You can generate an uberjar and execute it via java in the terminal:
```bash
# genarate a service.jar in the root of this repository.
clj -X:uberjar
# execute it via java
java -jar target/service.jar
```
