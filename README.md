<h2 style="text-align: center;">Stage #4: The shortest route</h2>

<h2 style="text-align: center;">Description</h2>

<p>Now that our metro has gotten big, the passengers are finding it difficult to find their way around it. Your task is to create an application that can pave the route from one station to another, displaying all the necessary stations and transitions. The application must also find the path from one point to another so that people don't have to be stuck underground for too long.</p>

<p>In the previous step, our data structure looked like a Graph, and we need to use a special algorithm to find the shortest way from one point to another. In this case, let's use the <strong>Breadth-First search algorithm</strong>, which is really common and quite easy to understand.</p>

<h2 style="text-align: center;">Objective</h2>

<ul>
	<li>Add a feature to search for a path with the command <code class="language-json">/route "[line 1]" "[station 1]" "[line 2]" "[station 2]"</code>.</li>
	<li>The program should print every passed station and every transition.</li>
</ul>

<p>For your updated metro map, you may use the real stations of <a target="_blank" href="https://en.wikipedia.org/wiki/Prague_Metro" rel="noopener noreferrer nofollow" target="_blank">Prague Metro</a>. Don't forget to <a target="_blank" href="https://stepik.org/media/attachments/lesson/373079/prague_subway.json" rel="noopener noreferrer nofollow">get the file</a> with the station names!</p>

<h2 style="text-align: center;">Examples</h2>

<p>The greater-than symbol followed by a space <code class="language-json">&gt; </code> represents the user input. Note that it's not part of the input.</p>

<p><strong>Input file example</strong></p>

<pre><code class="language-json">{
    "Metro-Railway": {
        "3": {
            "name": "Baker street",
            "transfer": {
                "line": "Hammersmith-and-City",
                "station": "Baker street"
            }
        },
        "1": {
            "name": "Bishops-road",
            "transfer": null
        },
        "2": {
            "name": "Edgver road",
            "transfer": null
        }
    },
    "Hammersmith-and-City": {
        "2": {
            "name": "Westbourne-park",
            "transfer": null
        },
        "1": {
            "name": "Hammersmith",
            "transfer": null
        },
        "3": {
            "name": "Baker street",
            "transfer": {
                "line": "Metro-Railway",
                "station": "Baker street"
            }
        }
    }
}</code></pre>

<p><strong>Example 1</strong></p>

<pre><code class="language-no-highlight">&gt; java Main test-file.json
&gt; /route Metro-Railway "Edgver road" Hammersmith-and-City Westbourne-park
Edgver road
Baker street
Transition to line Hammersmith-and-City
Baker street
Westbourne-park
&gt; exit</code></pre>

<p><strong>Example 2</strong></p>

<pre><code class="language-no-highlight">&gt; java Main test-file.json
&gt; /add Hammersmith-and-City "Test station"
&gt; /output Hammersmith-and-City
depot - Hammersmith - Westbourne-park
Hammersmith - Westbourne-park - Test station
Westbourne-park - Test station - depot
&gt; /exit</code></pre>

<p><strong>Example 3</strong> </p>

<pre><code class="language-no-highlight">&gt; java Main test-file.json
&gt; /remove Hammersmith-and-City Hammersmith
&gt; /output Hammersmith-and-City
depot - Westbourne-park - depot
&gt; /exit</code></pre>