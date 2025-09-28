# OrdenacaoJavaFX

## 📋 Sobre o Projeto

Este projeto foi desenvolvido como uma **Atividade Prática de Implementação** para a disciplina de **Pesquisa e Ordenação**, ministrada pelo Prof. Francisco Assis da Silva. O objetivo é criar uma aplicação JavaFX que demonstra visualmente a execução passo a passo de dois algoritmos de ordenação:

- **Merge Sort** (2ª Implementação)
- **Quick Sort sem pivô**

## 🎯 Funcionalidades

A aplicação oferece uma animação intuitiva e educativa que inclui:

1. **Geração aleatória** dos valores a serem ordenados
2. **Objetos visuais** com os valores sendo movidos na tela durante a ordenação
3. **Código-fonte** do método de ordenação com demarcação de cada linha em execução
4. **Indicadores visuais**: variáveis de índices, setas indicando o processo, cores diferenciadas, etc.

## 🔧 Tecnologias Utilizadas

- **Java** - Linguagem de programação principal
- **JavaFX** - Framework para interface gráfica e animações
- **Maven/Gradle** - Gerenciamento de dependências (se aplicável)

## 🚀 Como Executar

### Pré-requisitos

- Java JDK 8 ou superior
- JavaFX SDK (se não incluído no JDK)
- IDE Java (IntelliJ IDEA, Eclipse, NetBeans, etc.)

### Compilação e Execução

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/OrdenacaoJavaFX.git
cd OrdenacaoJavaFX
```

2. Compile o projeto:
```bash
javac -cp "path/to/javafx/lib/*" src/*.java
```

3. Execute a aplicação:
```bash
java -cp "path/to/javafx/lib/*:src" --module-path "path/to/javafx/lib" --add-modules javafx.controls,javafx.fxml Principal
```

## 📚 Algoritmos Implementados

### Merge Sort (2ª Implementação)
O Merge Sort é um algoritmo de ordenação eficiente, estável e baseado no paradigma "dividir para conquistar". Possui complexidade O(n log n) no pior caso.

**Características:**
- Divide o array recursivamente até ter elementos únicos
- Combina (merge) os sub-arrays de forma ordenada
- Garante estabilidade na ordenação

### Quick Sort sem Pivô
Uma variante do Quick Sort tradicional que não utiliza um elemento pivô específico para a partição dos dados.

**Características:**
- Algoritmo de ordenação por partição
- Complexidade média O(n log n)
- Implementação in-place (economia de memória)

## 🎨 Demonstração Visual

A aplicação utiliza elementos visuais JavaFX para demonstrar:

- **Botões coloridos** representando os elementos do array
- **Animações suaves** mostrando o movimento dos elementos
- **Código destacado** indicando a linha em execução
- **Indicadores visuais** como setas e índices
- **Controles de velocidade** para ajustar a animação

## 👥 Desenvolvimento

Este projeto foi desenvolvido como **trabalho em dupla** seguindo as especificações da atividade prática.
— Desenvolvedores:

Daniel Andreassi

Allan Maldonado

**Requisitos atendidos:**
- ✅ Animação visual dos algoritmos
- ✅ Geração aleatória de valores
- ✅ Movimentação de objetos na tela
- ✅ Exibição do código-fonte com execução destacada
- ✅ Indicadores visuais (índices, setas, cores)

## 📖 Referências

- Material da disciplina Pesquisa e Ordenação
- Documentação oficial do JavaFX
- Algoritmos clássicos de ordenação

## 📄 Licença

Este projeto é destinado exclusivamente para fins educacionais.
