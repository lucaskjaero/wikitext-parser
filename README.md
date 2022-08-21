# wikitext-parser

A library to work with content in Wikitext format.

## How to use this library.

1. Getting the abstract syntax tree (AST) and walking it.

The AST is a high level tree that represents the contents of the article conceptually. For example, the AST contains
nodes for sections, bolded text, links, and the like.

2. Converting the article to XML, which you can process using your tool of choice.

The generated XML has the same structure as the AST, so it is simple to switch.

## Walking the AST

You can get the AST using the Parser class. Here's a code sample.

```
WikiTextElement root = Parser.parse(inputWikiText);
```

The AST nodes are fully documented using javadocs. You can browse the documentation
at [lucaskjaero.github.io/wikitext-parser/](https://lucaskjaero.github.io/wikitext-parser/).

### AST Structure.

The AST is a tree structure composed of parent nodes, leaf nodes, and attributes.

- A parent node is one that has child nodes.
- A leaf node does not have child nodes.
- Attributes are attached to a wikitext node and are not a part of the tree by themselves.

## Getting XML

Generating XML is simple. You can generate XML from the AST using this sample:

```
String xml = root.toXML();
```

Or if you don't want the AST at all, you can generate the xml directly from the input.

```
String xml = Parser.parseToString(inputWikiText);
```

## Learn more

[Read more about WikiText on Wikipedia](https://en.wikipedia.org/wiki/Help:Wikitext)