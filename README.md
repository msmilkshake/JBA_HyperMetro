<h2 style="text-align: center;">Stage #5: The fastest route</h2>

<h2 style="text-align: center;">Description</h2>

<p>Good job: now the passengers can easily find their way around the metro! However, helping them plan their travel time requires some more effort. We did not take into account one important detail: the distances between the stations vary, which means that the travel time varies as well. We need to find not just the shortest path, but the fastest one. People who are late for work will thank you! You have all the necessary information about the distance between the stations: pay attention to the example!</p>

<p>The kind of graph we need here is called <strong>a weighted graph</strong> because its edges have "weight", or, in other words, value. To solve this specific problem, the algorithm from the previous stage cannot be used. <a target="_blank" href="https://brilliant.org/wiki/dijkstras-short-path-finder/" rel="noopener noreferrer nofollow" target="_blank">Dijkstra's algorithm</a>, on the other hand, is a great choice for this task! It is also a common algorithm for finding the shortest path, but it takes into account the weight of the edges. To get a better understanding, you can take a look at a <a target="_blank" href="https://www.cs.usfca.edu/~galles/visualization/Dijkstra.html" rel="noopener noreferrer nofollow" target="_blank">visualization</a> of the algorithm.</p>

<h2 style="text-align: center;">Objective</h2>

<ul>
	<li>Add the ability to find the fastest way using the command <code class="language-json">/fastest-route "[line 1]" "[station 1]" "[line 2]" "[station 2]"</code>.</li>
	<li>The program should print the estimate total travel time.</li>
	<li>Upgrade the <em>add station</em> command by adding the travel time.</li>
	<li>Take it into account that transferring from one line to another takes 5 minutes.</li>
</ul>

<p>For your updated metro map, you may use the real stations of <a target="_blank" href="https://en.wikipedia.org/wiki/Prague_Metro" rel="noopener noreferrer nofollow" target="_blank">Prague Metro</a>. Don't forget to <a target="_blank" href="https://stepik.org/media/attachments/lesson/373079/prague_subway.json" rel="noopener noreferrer nofollow">get the file</a> with the station names!</p>

<h2 style="text-align: center;">Example</h2>

<p>The greater-than symbol followed by a space <code class="language-json">&gt; </code> represents the user input. Note that it's not part of the input.</p>

<p><strong>File example</strong></p>

<pre><code class="language-json">{
    "Metro-Railway": {
        "3": {
            "name": "Baker street",
            "transfer": {
                "line": "Hammersmith-and-City",
                "station": "Baker street"
            },
            "time": "1"
        },
        "1": {
            "name": "Bishops-road",
            "transfer": null,
            "time": "2"
        },
        "2": {
            "name": "Edgver road",
            "transfer": null,
            "time": "3"
        }
    },
    "Hammersmith-and-City": {
        "2": {
            "name": "Westbourne-park",
            "transfer": null,
            "time": "3"
        },
        "1": {
            "name": "Hammersmith",
            "transfer": null,
            "time": "1"
        },
        "3": {
            "name": "Baker street",
            "transfer": {
                "line": "Metro-Railway",
                "station": "Baker street"
            },
            "time": "3"
        }
    }
}</code></pre>

<p><strong>Example 1</strong></p>

<pre><code class="language-no-highlight">&gt; java Main test-file.json
&gt; /fastest-route Hammersmith-and-City "Baker street" Hammersmith-and-City Hammersmith
Baker street
Westbourne-park
Hammersmith
Total: 4 minutes in the way
&gt; /exit</code></pre>

<p><strong>Example 2</strong></p>

<pre><code class="language-no-highlight">&gt; java Main test-file.json
&gt; add Hammersmith-and-City New-Station 4
&gt; /exit</code></pre>

<p><strong>Example 3</strong></p>

<pre><code class="language-no-highlight">&gt; java Main test-file.json
&gt; /remove Hammersmith-and-City Hammersmith
&gt; /output Hammersmith-and-City
depot - Westbourne-park - depot
&gt; /exit</code></pre>