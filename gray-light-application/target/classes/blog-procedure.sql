create procedure findBlogOwnerId(IN owner_id INTEGER, IN start INTEGER, IN count INTEGER)
begin
    IF start >= 0 & count >= 0 THEN
        select b.*
        from blog b
        where b.owner_id = owner_id
        limit start, count;
        select COUNT(*) as total
        from blog b
        where b.owner_id = owner_id;
    ELSE
        select b.*
        from blog b
        where b.owner_id = owner_id;
    END IF;
end;
/*
create procedure findBlogByOwnerIdAndTags(IN owner_id INTEGER, IN array )*/
