# deps-new-wrap
Wrapper over [deps-new](https://github.com/seancorfield/deps-new) to give a more user friendly CLI

## Installation
You can install tools adding the following `:alias` into your local `~/.clojure/deps.edn` file:
```clojure
:new {:extra-deps {cc.delboni/deps-new-wrap
                   {:git/url "https://github.com/rafaeldelboni/deps-new-wrap"
                    :git/sha "a642ccc85d3c745cde71df548f9836db89bb5118"}}
      :exec-fn cc.delboni.main/new}
```

## Usage
```bash
# For a template that is created with the github username/name as namespace
clj -X:new :gh rafaeldelboni/helix-scratch :name myusername/mynewapp

# If the namespace is different from the repository owner
clj -X:new :gh rafaeldelboni/helix-scratch-clojars :template cc.delboni/helix-scratch :name myusername/mynewapp
```

### Github
```bash
clj -X:new :gh rafaeldelboni/helix-scratch :name myusername/mynewapp
```
### Gitlab
```bash
clj -X:new :gl rafaeldelboni/helix-scratch :name myusername/mynewapp
```
### Bitbucket
```bash
clj -X:new :bb rafaeldelboni/helix-scratch :name myusername/mynewapp
```
### Sourcehut
```bash
clj -X:new :sh rafaeldelboni/helix-scratch :name myusername/mynewapp
```
### Codeberg
```bash
clj -X:new :cb rafaeldelboni/helix-scratch :name myusername/mynewapp
```
### Git Url
```bash
clj -X:new :git https://github.com/rafaeldelboni/helix-scratch.git :template :rafaeldelboni/helix-scratch :name myusername/mynewapp
```

### Default
```bash
# You can always use deps-new default commands as well
clj -X:new :name myname/myapp
clj -X:new :template lib :name myname/my-cool-lib
clj -X:new :template scratch :name poc/simple-app
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
