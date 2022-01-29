package com.mybatis.demo.repository;

import com.mybatis.demo.entity.Book;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BookRepository {

  @Select("SELECT id, name, isbn, price, author FROM BOOKS")
  List<Book> findAll();

  @Select("SELECT * FROM BOOKS WHERE id = #{id}")
  Book findOne(@Param("id") Long id);

  @Delete("DELETE FROM BOOKS WHERE id = #{id}")
  void delete(Long id);

  @Options(useGeneratedKeys = true)
  @Insert("INSERT INTO BOOKS (name, isbn, price, author) VALUES( #{name}, #{isbn}, #{price}, #{author} )")
  void insert(Book book);

  @Update("UPDATE BOOKS SET name = #{name}, isbn = #{isbn}, price = #{price}, author = #{author} WHERE id = #{id}")
  void update(Book book);

}
