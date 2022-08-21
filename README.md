# wikitext-parser

A library to work with content in Wikitext format.

## How to use this library

1. Getting the abstract syntax tree (AST) and walking it.

The AST is a high level tree that represents the contents of the article conceptually. For example, the AST contains
nodes for sections, bolded text, links, and the like.

2. Converting the article to XML, which you can process using your tool of choice.

The generated XML has the same structure as the AST, so it is simple to switch.

## Walking the AST

You can get the AST using the Parser class. Here's a code sample.

```java
WikiTextElement root = WikiTextParser.parse(inputWikiText);
```

The AST nodes are fully documented using javadocs. You can browse the documentation
at [lucaskjaero.github.io/wikitext-parser/](https://lucaskjaero.github.io/wikitext-parser/).

### AST Structure

The AST is a tree structure composed of parent nodes, leaf nodes, and attributes.

- A parent node is one that has child nodes.
- A leaf node does not have child nodes.
- Attributes are attached to a wikitext node and are not a part of the tree by themselves.

## Getting XML

Generating XML is simple. You can generate XML from the AST using this sample:

```java
WikiTextElement root = WikiTextParser.parse(inputWikiText);
String xml = root.toXML();
```

Or if you don't want the AST at all, you can generate the xml directly from the input.

```java
String xml = WikiTextParser.parseToString(inputWikiText);
```

## Learn more

[Read more about WikiText on Wikipedia](https://en.wikipedia.org/wiki/Help:Wikitext)

## Contributing

### Issues

Spotted a bug? Did I miss implementing part of the WikiText spec? Open a github issue and tell me about it.

### Pull request

I'm happy to accept pull requests. Here's what I'll need from all contributions:

- ANTLR parse grammar is free of actions.
- All existing tests pass or have been updated.
- New AST nodes have tests written for them.
- Code formatter is run.

Bottom line: If you can run `./gradlew build` successfully and all github checks pass, then you should be good to go.

#### Building code

Basic parser code is built using antlr, which is generated at build time. To generate this code, run `./gradlew build`.
After this, the project should successfully compile. If you change the grammar, you will need to run this again.

#### Code formatter

To format code, run `./gradlew spotlessApply` and your code will automatically be reformatted. This will also be done
before committing, to make the process as painless as possible.

#### Confused

Want to help out but aren't sure how to begin? Create an issue and I'll be happy to help you out.