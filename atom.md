I added Atom feeds to the blog engine that runs this site.
It was easy to add because of the uniformity of data representation.
My blog is a list of maps containing the keys :title :updated and :content.

Basically all I needed to do was add a Clojure atom feed generator to my
dependency list and then mapped the function to generate the Atom xml for
each post after I generated the required fields from each post in the same
mapping using **comp**, then I stitched it together with atom-feed with the
feed information. Or in [code](http://github.com/georgerogers42/my-blog).

	(atom-feed feed-metadata (map (comp atom-entry atomize-post) posts))

