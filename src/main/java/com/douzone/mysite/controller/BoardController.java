package com.douzone.mysite.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.repository.CommentDao;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;
import com.douzone.mysite.vo.UserVo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String list(HttpServletRequest request,Model model) {
		boardService.list(request,model);
		return "/board/list";
	}
	
	@RequestMapping(value="/write",method=RequestMethod.GET)
	public String write(HttpServletRequest request) {
		UserVo authUser=null;
		HttpSession session = request.getSession();
		if(session != null) {
			authUser = (UserVo)session.getAttribute("authuser");
		}
		if(authUser == null) {
			return "/main";
		}
		return "/board/write";
	}
	
	@RequestMapping(value="/write",method=RequestMethod.POST)
	public String write2(HttpServletRequest request) {
		UserVo authUser=null;
		HttpSession session = request.getSession();
		if(session != null) {
			authUser = (UserVo)session.getAttribute("authuser");
		}
		if(authUser == null) {
			return "/main";
		}
		
		
		
		
		
		ServletContext context =request.getSession().getServletContext(); //어플리케이션에 대한 정보를 ServletContext 객체가 갖게 됨. 
          String saveDir = context.getRealPath("/Upload"); //절대경로를 가져옴
          System.out.println("절대경로 >> " + saveDir);
          
          File Folder = new File(saveDir);

        	// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
        	if (!Folder.exists()) {
        		try{
        		    Folder.mkdir(); //폴더 생성합니다.
        		    System.out.println("폴더가 생성되었습니다.");
        	        } 
        	        catch(Exception e){
        		    e.getStackTrace();
        		}        
                 }else {
        		System.out.println("이미 폴더가 생성되어 있습니다.");
        	}
          
          
          
          int maxSize = 3*1024*1024;
          String encoding = "UTF-8";
          MultipartRequest multi =null;
		try {
			multi = new MultipartRequest(request, saveDir, maxSize, encoding, new DefaultFileRenamePolicy());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          String fileName = multi.getFilesystemName("file");
          if(fileName==null) {
        	  fileName="";
          }
          
      	


          
          System.out.println(fileName);
          String div = multi.getParameter("a"); 
          String title = multi.getParameter("title");
  		  String contents = multi.getParameter("contents");
  		  if(title.equals("")||title.equals(null)||contents.equals("")||contents.equals(null)) {
  			//WebUtils.redirect(request, response, "/mysite2/board");
  			return "redirect:/board";
  		  }
  		  long userno = authUser.getNo();
          
          
  		BoardVo vo = null; 
        if("write".equals(div)) {
  		int gno=new BoardDao().getMaxGno()+1;
  		int ono=1;
  		int depth=0;
  		
  		vo = new BoardVo();
  		vo.setgNo(gno);
  		vo.setoNo(ono);
  		vo.setDepth(depth);
  		
  		
        }  else if("reply".equals(div)) {
  		long no =Long.parseLong(multi.getParameter("no"));
		vo = new BoardDao().getVo(no);
		vo.setoNo(new BoardDao().getMaxono(vo.getgNo())+1);
		vo.setDepth(vo.getDepth()+1);
        }
        vo.setFileName(fileName);
        vo.setHit(0);
        vo.setTitle(title);
  		vo.setContents(contents);
  		vo.setUserNo(userno);
  		new BoardDao().insert(vo);
		
  		request.setAttribute("filePath",saveDir+"\\"+vo.getFileName());
  		System.out.println("--------"+saveDir+"\\"+vo.getFileName());
          //WebUtils.redirect(request, response, "/mysite2/board");
          return "redirect:/board";
	}
	@RequestMapping("/view")
	public String view(HttpServletRequest request,HttpServletResponse response,String no) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		BoardDao dao = new BoardDao();

		BoardVo vo = dao.getVo(Long.parseLong(no));
		
		request.setAttribute("vo", vo);
		
		
		List<CommentVo> commentList = new CommentDao().getList( Long.parseLong(no));
		request.setAttribute("commentList",commentList);
		//ServletContext context = request.getSession().getServletContext(); //어플리케이션에 대한 정보를 ServletContext 객체가 갖게 됨. 
       // String saveDir = context.getRealPath("Upload");
        if(vo.getFileName()!=null) {
		request.setAttribute("filePath","Upload/"+vo.getFileName());
		request.setAttribute("fileName",vo.getFileName());
        }
		System.out.println("Upload/"+vo.getFileName());
		
		
		 // 쿠키값 가져오기
	    Cookie[] cookies = request.getCookies() ;
	     
	    if(cookies != null){
	         
	        for(int i=0; i < cookies.length; i++){
	            Cookie c = cookies[i] ;
	             
//	            // 저장된 쿠키 이름을 가져온다
//	            String cName = c.getName();
	             
	            // 쿠키값을 가져온다
	            String cValue = c.getValue() ;
	             if(cValue.equals(no)) {
	            	 System.out.println("쿠키 있음");
	            	 return "/board/view";
	             }
	             
	        }
	    }


		 
	    Cookie c = new Cookie(no, no) ;
	     
	    // 쿠키에 설명을 추가한다
	    c.setComment("게시물 번호") ;
	     
	    // 쿠키 유효기간을 설정한다. 초단위 : 60*60*24= 1일
	    c.setMaxAge(60*60*24) ;
	     
	    // 응답헤더에 쿠키를 추가한다.
	    response.addCookie(c) ;

	    
		  dao.update(Long.parseLong(no));//조회수증가
		  System.out.println("쿠키 없음");
		  return "/board/view";
		
	}
	
	@RequestMapping("/remove")
	public String remove(HttpServletRequest request,Long no) {
		UserVo authUser=null;
		HttpSession session = request.getSession();
		if(session != null) {
			authUser = (UserVo)session.getAttribute("authuser");
		}
		if(authUser == null) {
			return "/main";
		}

		BoardVo vo = new BoardDao().getVo(no);
		List<BoardVo> list = new BoardDao().getList();
		
		 String fileName = vo.getFileName(); //지울 파일명
		 String root = request.getSession().getServletContext().getRealPath("/Upload/");
 	    String filePath = root + fileName;
		 
		 
		 
		   
		   File f = new File(filePath); // 파일 객체생성
		   if( f.exists()) f.delete(); // 파일이 존재하면 파일을 삭제한다.
		
		   new CommentDao().deleteAll(no);
		   
		   
		for(BoardVo v : list) {
			if(v.getgNo() == vo.getgNo() && v.getDepth()>vo.getDepth()) {
				new BoardDao().updateCheck(no);
				return "redirect:/board";
			}
		}
		
		new BoardDao().remove(no);
		return "redirect:/board";
		
	}
	@RequestMapping(value="/modify",method=RequestMethod.GET)
	public String modify(HttpServletRequest request,Long no) {
		UserVo authUser=null;
		HttpSession session = request.getSession();
		if(session != null) {
			authUser = (UserVo)session.getAttribute("authuser");
		}
		
		BoardVo vo = new BoardDao().getVo(no);
		if(authUser == null || authUser.getNo() != vo.getUserNo()) {
			return "redirect:/board";
		}
		
		
		request.setAttribute("vo", vo);

		return "/board/modify";
	}
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modify(HttpServletRequest request,BoardVo vo) {
		UserVo authUser=null;
		HttpSession session = request.getSession();
		if(session != null) {
			authUser = (UserVo)session.getAttribute("authuser");
		}
		//long no = Long.parseLong(request.getParameter("no"));
		if(authUser == null) {
			return "redirect:/board";
		}
		
		//String title = request.getParameter("title");
		//String contents = request.getParameter("content");
		
		//BoardVo vo = new BoardVo();
		//vo.setTitle(title);
		//vo.setContents(contents);
		//vo.setNo(no);
		new BoardDao().update(vo);
		
		return "redirect:/board";

	}
	@RequestMapping("/comment")
	public String comment(HttpServletRequest request,Long boardNo,Long authUserNo,String contents) {
		UserVo authUser=null;
		HttpSession session = request.getSession();
		if(session != null) {
			authUser = (UserVo)session.getAttribute("authuser");
		}
		if(authUser == null) {
			return "/main";
		}

		if(contents.equals("")||contents.equals(null)) {
			return "/board/view?no="+boardNo;
		}
		CommentVo vo = new CommentVo();
		vo.setBoardNo(boardNo);
		vo.setUserNo(authUserNo);
		vo.setContents(contents);
		System.out.println(vo);
		new CommentDao().insert(vo);
		
		return "redirect:/board/view?no="+boardNo;
		

	}
	@RequestMapping("/commentremove")
	public String commentremove(HttpServletRequest request,Long boardNo,Long no) {
		UserVo authUser=null;
		HttpSession session = request.getSession();
		if(session != null) {
			authUser = (UserVo)session.getAttribute("authuser");
		}
		if(authUser == null) {
			return "/main";
		}
		new CommentDao().delete(no);
		return "redirect:/board/view?no="+boardNo;

	}
	@RequestMapping(value="/reply",method=RequestMethod.GET)
	public String reply(HttpServletRequest request,String no) {
		request.setAttribute("no", no);
		return "/board/write";
	}
	@RequestMapping(value="/reply",method=RequestMethod.POST)
	public String reply(HttpServletRequest request,Long no, String title, String contents ) {
		UserVo authUser=null;
		HttpSession session = request.getSession();
		if(session != null) {
			authUser = (UserVo)session.getAttribute("authuser");
		}
		if(authUser == null) {
			return "/main";
		}
		//long no =Long.parseLong(request.getParameter("no"));
		BoardVo vo = new BoardDao().getVo(no);
		//String title = request.getParameter("title");
		//String contents = request.getParameter("contents");
		long userno = authUser.getNo();
		
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setHit(0);
		vo.setoNo(new BoardDao().getMaxono(vo.getgNo())+1);
		vo.setDepth(vo.getDepth()+1);
		vo.setUserNo(userno);

		new BoardDao().insert(vo);
		
		return "redirect:/board";
	}
	
	@RequestMapping("/download")
	public void download(HttpServletRequest request,HttpServletResponse response,String fileName) {
		  try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	  
    	  
  	    // 파일 업로드된 경로
  	    String root = request.getSession().getServletContext().getRealPath("/");
  	    String savePath = root + "Upload";
  	 
  	    // 서버에 실제 저장된 파일명
  	    String filename = request.getParameter("fileName");
  	     
  	    // 실제 내보낼 파일명
  	    String orgfilename =filename ;
  	      
  	 
  	    InputStream in = null;
  	    OutputStream os = null;
  	    File file = null;
  	    boolean skip = false;
  	    String client = "";
  	 
  	 
  	    try{
  	         
  	 
  	        // 파일을 읽어 스트림에 담기
  	        try{
  	            file = new File(savePath, filename);
  	            in = new FileInputStream(file);
  	        }catch(FileNotFoundException fe){
  	            skip = true;
  	        }
  	 
  	 
  	 
  	         
  	        client = request.getHeader("User-Agent");
  	 
  	        // 파일 다운로드 헤더 지정
  	        response.reset() ;
  	        response.setContentType("application/octet-stream");
  	        response.setHeader("Content-Description", "JSP Generated Data");
  	 
  	 
  	        if(!skip){
  	 
  	             
  	            // IE
  	            if(client.indexOf("MSIE") != -1){
  	                response.setHeader ("Content-Disposition", "attachment; filename="+new String(orgfilename.getBytes("KSC5601"),"ISO8859_1"));
  	 
  	            }else{
  	                // 한글 파일명 처리
  	                orgfilename = new String(orgfilename.getBytes("utf-8"),"iso-8859-1");
  	 
  	                response.setHeader("Content-Disposition", "attachment; filename=\"" + orgfilename + "\"");
  	                response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
  	            } 
  	             
  	            response.setHeader ("Content-Length", ""+file.length() );
  	 
  	 
  	       
  	            os = response.getOutputStream();
  	            byte b[] = new byte[(int)file.length()];
  	            int leng = 0;
  	             
  	            while( (leng = in.read(b)) > 0 ){
  	                os.write(b,0,leng);
  	            }
  	 
  	        }else{
  	            response.setContentType("text/html;charset=UTF-8");
  	 
  	        }
  	         
  	        in.close();
  	        os.close();
  	 
  	    }catch(Exception e){
  	      e.printStackTrace();
  	    }
	}
}
