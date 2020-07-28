create procedure findTagByOwnerId(in ownerId long, IN start INTEGER, IN count INTEGER)
begin
    IF start >= 0 & count >= 0 THEN

        select tag.*
        from tag
                 inner join
             (select blog_tag.tag
              from blog
                       inner join blog_tag on blog.owner_id = ownerId
              group by blog_tag.tag) as bbtt
             on tag.name = bbtt.tag
        order by tag.name
        limit start, count;

        select COUNT(*) as total
        from tag
                 inner join
             (select blog_tag.tag
              from blog
                       inner join blog_tag on blog.owner_id = ownerId
              group by blog_tag.tag) as bbtt
             on tag.name = bbtt.tag
        order by tag.name;
    ELSE

        select tag.*
        from tag
                 inner join
             (select blog_tag.tag
              from blog
                       inner join blog_tag on blog.owner_id = ownerId
              group by blog_tag.tag) as bbtt
             on tag.name = bbtt.tag
        order by tag.name;

    end if;
end;