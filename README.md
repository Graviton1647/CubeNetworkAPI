---
description: >-
  CubeAPI is made to go with Bukkit and uses it for the backend this is for QOL
  and quicker plugin development .
---

# CubeAPI

## Installing CubeAPI

Installing CubeAPI is a fairly straight forward process:

{% tabs %}
{% tab title="Gradle" %}
Find **build.gradle** Under repositories add 

```text
maven { url 'https://jitpack.io' }
```

Now under dependencies add 

```text
compile 'com.github.Graviton1647:CubeNetworkAPI:2.0'
```
{% endtab %}

{% tab title="Maven" %}
Find **pom.xml** and under dependencies add

```bash
<dependency>
    <groupId>com.github.Graviton1647</groupId>
    <artifactId>CubeNetworkAPI</artifactId>
    <version>2.0</version>
</dependency>
```

Now under **repositories** add 

```bash
<repository>
          <id>jitpack</id>
          <url>https://jitpack.io</url>
</repository>
```
{% endtab %}
{% endtabs %}





